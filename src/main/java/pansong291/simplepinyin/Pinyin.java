package pansong291.simplepinyin;


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
        String[] result = null;

        int charIndex = getPinyinCode(c);
        if (charIndex < 0) {
            return null;
        } else if (charIndex == 0) {
            result = new String[]{PinyinData.PINYIN_12295};
        } else {
            String[] duoyin = getDuoyin(c);
            if (duoyin == null) {
                result = new String[]{PinyinData.PINYIN_TABLE[charIndex]};
            } else {
                result = new String[duoyin.length + 1];
                result[0] = PinyinData.PINYIN_TABLE[charIndex];
                System.arraycopy(duoyin, 0, result, 1, duoyin.length);
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
