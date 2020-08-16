package pansong291.simplepinyin;


import com.intellij.openapi.util.Pair;

import java.util.*;

/**
 * @author pansong291
 * @author tuchg
 * @date 2018/9/12
 * @date 2020/8/7
 */
public final class Pinyin {

    /**
     * 全部大写
     */
    public static final int UP_CASE = -1;
    /**
     * 首字母大写
     */
    public static final int FIRST_UP_CASE = 0;
    /**
     * 全部小写
     */
    public static final int LOW_CASE = 1;

    /**
     * 拼音匹配的一些缓存
     */
    private static final HashMap<String, List<String>> CACHE = new HashMap<>();

    private Pinyin() {
    }

    /**
     * 将输入字符串转为拼音，以字符为单位插入分隔符，多个拼音只取其中一个
     * <p>
     * 例: "hello:中国！"  在separator为","时，输出： "H,E,L,L,O,:,Zhong,Guo,!"
     *
     * @param str       输入字符串
     * @param separator 分隔符
     * @return 中文转为拼音的字符串
     */
    public static String toPinyin(String str, String separator) {
        return toPinyin(str, separator, Pinyin.FIRST_UP_CASE);
    }

    /**
     * 是否含有中文字
     *
     * @param str
     * @return
     */
    public static boolean hasChinese(String str) {

        for (char c : str.toCharArray()) {
            if ((PinyinData.MIN_VALUE <= c && c <= PinyinData.MAX_VALUE
                    && getPinyinCode(c) > 0)
                    || PinyinData.CHAR_12295 == c) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将输入字符串转为拼音，以字符为单位插入分隔符，多个拼音只取其中一个
     *
     * @param str       输入字符串
     * @param separator 分隔符
     * @param caseType  大小写类型
     * @return 中文转为拼音的字符串
     */
    public static String toPinyin(String str, String separator, int caseType) {
        if (str == null || str.length() == 0) {
            return str;
        }

        StringBuilder resultPinyinStrBuf = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            resultPinyinStrBuf.append(Pinyin.toPinyin(str.charAt(i), caseType)[0]);
            if (i != str.length() - 1) {
                resultPinyinStrBuf.append(separator);
            }
        }
        return resultPinyinStrBuf.toString();
    }

    /**
     * 获取多音字组合
     *
     * @param str      输入字符串
     * @param caseType 大小写类型
     * @return 多音字拼接的结果数组
     * @Author tuchg
     */

    public static List<String> toPinyin(String str, int caseType) {
        String key = str + caseType;
        if (CACHE.containsKey(key)) {
            return CACHE.get(key);
        }
        if (str == null || str.length() == 0) {
            return null;
        }

        List<String[]> compose = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            compose.add(Pinyin.toPinyin(str.charAt(i), caseType));
        }
        // DFS 栈  /todo Vector 同步性能损耗
        Stack<Pair<Integer, Integer>> stack = new Stack<>();
        // 路径栈
        Stack<Pair<Integer, Integer>> pathStack = new Stack<>();
        //返回的所有路径栈
        List<String> pathList = new LinkedList<>();


        for (int m = 0; m < compose.get(0).length; m++) {
            Pair<Integer, Integer> root = Pair.create(0, m);
            // 存入根节点
            stack.push(root);
            pathStack.push(root);
            while (!stack.isEmpty()) {
                int size = stack.size();
                for (int i = 0; i < size; i++) {
                    Pair<Integer, Integer> cur = stack.pop();
                    int index = cur.first + 1;

                    // 树边界
                    if (index >= compose.size()) {
                        pathStack.push(Pair.create(cur.first, cur.second));
                        StringBuilder sb = new StringBuilder();
                        // 读取当前路径，构建相应字符串
                        pathStack.stream().skip(1).forEach(curPair -> {
                            sb.append(compose.get(curPair.first)[curPair.second]);
                        });
                        pathList.add(sb.toString());
                        pathStack.pop();
                        continue;
                    }


                    if (pathStack.size() >= str.length()) {
                        // 暴力纠正
                        pathStack.insertElementAt(cur, index);
                        pathStack.removeElementAt(index + 1);
                    } else {
                        pathStack.push(cur);
                    }


                    int len = compose.get(index).length;
                    for (int j = 0; j < len; j++) {
                        stack.push(Pair.create(index, j));
                    }
                }
            }
            pathStack.clear();
            stack.clear();
        }


        CACHE.put(key, pathList);
        return pathList;
    }


    /**
     * 若输入字符是中文则转为拼音，若不是则返回该字符，支持多音字
     *
     * @param c 输入字符
     * @return 拼音字符串数组
     */
    public static String[] toPinyin(char c) {
        return toPinyin(c, Pinyin.FIRST_UP_CASE);
    }

    /**
     * 若输入字符是中文则转为拼音，若不是则返回该字符，支持多音字
     *
     * @param c        输入字符
     * @param caseType 大小写类型
     * @return 拼音字符串数组
     */
    public static String[] toPinyin(char c, int caseType) {
        String[] result = getPinyin(c, caseType);

        if (result == null) {
            String str = String.valueOf(c);

            switch (caseType) {
                case UP_CASE:
                case FIRST_UP_CASE:
                    str = str.toUpperCase();
                    break;
                case LOW_CASE:
                    str = str.toLowerCase();
                    break;
            }

            result = new String[]{str};
        }
        return result;
    }

    /**
     * 若输入字符是中文则转为拼音，若不是则返回null，支持多音字
     * 可用于判断该字符是否是中文汉字
     *
     * @param c 输入字符
     * @return 拼音字符串数组
     */
    public static String[] getPinyin(char c) {
        return getPinyin(c, Pinyin.FIRST_UP_CASE);
    }

    /**
     * 若输入字符是中文则转为拼音，若不是则返回null，支持多音字
     * 可用于判断该字符是否是中文汉字
     *
     * @param c        输入字符
     * @param caseType 大小写类型
     * @return 拼音字符串数组
     */
    public static String[] getPinyin(char c, int caseType) {
        String result[] = null;

        int charIndex = getPinyinCode(c);
        if (charIndex < 0) {
            return result;
        } else if (charIndex == 0) {
            result = new String[]{PinyinData.PINYIN_12295};
        } else {
            String duoyin[] = getDuoyin(c);
            if (duoyin == null) {
                result = new String[]{PinyinData.PINYIN_TABLE[charIndex]};
            } else {
                result = new String[duoyin.length + 1];
                result[0] = PinyinData.PINYIN_TABLE[charIndex];
                for (int i = 0; i < duoyin.length; i++) {
                    result[i + 1] = duoyin[i];
                }
            }
        }

        for (int i = 0; i < result.length; i++) {
            switch (caseType) {
                case UP_CASE:
                    result[i] = result[i].toUpperCase();
                    break;
                case LOW_CASE:
                    result[i] = result[i].toLowerCase();
                    break;
                case FIRST_UP_CASE:
                    break;
            }
        }
        return result;
    }

    /**
     * 删除小写字母
     *
     * @param firstUpCase 首字母大写的拼音
     * @return 拼音首字母
     */
    public static String deleteLowerCase(String firstUpCase) {
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < firstUpCase.length(); i++) {
            c = firstUpCase.charAt(i);
            if (c < 'a' || c > 'z') {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static int getPinyinCode(char c) {
        if (PinyinData.CHAR_12295 == c) {
            return 0;
        } else if (c < PinyinData.MIN_VALUE || c > PinyinData.MAX_VALUE) {
            return -1;
        }
        int offset = c - PinyinData.MIN_VALUE;
        if (offset < PinyinData.PINYIN_CODE_1_OFFSET) {
            return decodeIndex(PinyinCode1.PINYIN_CODE_PADDING, PinyinCode1.PINYIN_CODE, offset);
        } else if (offset < PinyinData.PINYIN_CODE_2_OFFSET) {
            return decodeIndex(PinyinCode2.PINYIN_CODE_PADDING, PinyinCode2.PINYIN_CODE,
                    offset - PinyinData.PINYIN_CODE_1_OFFSET);
        } else {
            return decodeIndex(PinyinCode3.PINYIN_CODE_PADDING, PinyinCode3.PINYIN_CODE,
                    offset - PinyinData.PINYIN_CODE_2_OFFSET);
        }
    }

    static String[] getDuoyin(char c) {
        String[] duoyin = null;
        short[] duoyinCode = null;
        int i = DuoyinCode.getIndexOfDuoyinCharacter(c);
        if (i >= 0) {
            duoyinCode = DuoyinCode.decodeDuoyinIndex(i);
            duoyin = new String[duoyinCode.length];
            for (int j = 0; j < duoyinCode.length; j++) {
                duoyin[j] = PinyinData.PINYIN_TABLE[duoyinCode[j]];
            }
        }
        return duoyin;
    }

    private static short decodeIndex(byte[] paddings, byte[] indexes, int offset) {
        int index1 = offset / 8;
        int index2 = offset % 8;
        //低8位
        short realIndex = (short) (indexes[offset] & 0xff);
        //高1位，非0即1
        if ((paddings[index1] & PinyinData.BIT_MASKS[index2]) != 0) {
            realIndex = (short) (realIndex | PinyinData.PADDING_MASK);
        }
        return realIndex;
    }

}
