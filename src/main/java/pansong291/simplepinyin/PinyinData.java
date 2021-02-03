package pansong291.simplepinyin;

/**
 * Created by pansong291 on 2018/9/12.
 */
final class PinyinData
{

 public static final char MIN_VALUE = 19968;

 public static final char MAX_VALUE = 40869;

 static final String PINYIN_12295 = "Ling";

 static final char CHAR_12295 = 12295;

 static final int PINYIN_CODE_1_OFFSET = 7000;

 static final int PINYIN_CODE_2_OFFSET = 7000 * 2;

 static final int[] BIT_MASKS = new int[]{0x1, 0x2, 0x4, 0x8, 0x10, 0x20, 0x40, 0x80};

 static final int[] TWO_BIT_MASKS = new int[]{0xc0, 0x30, 0xc, 0x3};

 static final int PADDING_MASK = 256;

 private PinyinData(){}

 //dei,eng,hng,lo,lue,n,qui,r,tei, not find
 static final String[] PINYIN_TABLE = new String[]{"", "A", "Ai", "An", "Ang", "Ao", "Ba", "Bai",
  "Ban", "Bang", "Bao", "Bei", "Ben", "Beng", "Bi", "Bian", "Biao", "Bie", "Bin", "Bing",
  "Bo", "Bu", "Ca", "Cai", "Can", "Cang", "Cao", "Ce", "Cen", "Ceng", "Cha", "Chai",
  "Chan", "Chang", "Chao", "Che", "Chen", "Cheng", "Chi", "Chong", "Chou", "Chu", "Chuai",
  "Chuan", "Chuang", "Chui", "Chun", "Chuo", "Ci", "Cong", "Cou", "Cu", "Cuan", "Cui",
  "Cun", "Cuo", "Da", "Dai", "Dan", "Dang", "Dao", "De", "Deng", "Di", "Dia", "Dian",
  "Diao", "Die", "Ding", "Diu", "Dong", "Dou", "Du", "Duan", "Dui", "Dun", "Duo", "E",
  "Ei", "En", "Er", "E^", "Fa", "Fan", "Fang", "Fei", "Fen", "Feng", "Fo", "Fou", "Fu",
  "Ga", "Gai", "Gan", "Gang", "Gao", "Ge", "Gei", "Gen", "Geng", "Gong", "Gou", "Gu",
  "Gua", "Guai", "Guan", "Guang", "Gui", "Gun", "Guo", "Ha", "Hai", "Han", "Hang", "Hao",
  "He", "Hei", "Hen", "Heng", "Hong", "Hou", "Hu", "Hua", "Huai", "Huan", "Huang", "Hui",
  "Hun", "Huo", "Ji", "Jia", "Jian", "Jiang", "Jiao", "Jie", "Jin", "Jing", "Jiong",
  "Jiu", "Ju", "Juan", "Jue", "Jun", "Ka", "Kai", "Kan", "Kang", "Kao", "Ke", "Ken",
  "Keng", "Kong", "Kou", "Ku", "Kua", "Kuai", "Kuan", "Kuang", "Kui", "Kun", "Kuo", "La",
  "Lai", "Lan", "Lang", "Lao", "Le", "Lei", "Leng", "Li", "Lia", "Lian", "Liang", "Liao",
  "Lie", "Lin", "Ling", "Liu", "Long", "Lou", "Lu", "Luan", "Lun", "Luo", "Lv", "Lve",
  "M", "Ma", "Mai", "Man", "Mang", "Mao", "Me", "Mei", "Men", "Meng", "Mi", "Mian",
  "Miao", "Mie", "Min", "Ming", "Miu", "Mo", "Mou", "Mu", "Na", "Nai", "Nan", "Nang",
  "Nao", "Ne", "Nei", "Nen", "Neng", "Ng", "Ni", "Nian", "Niang", "Niao", "Nie", "Nin",
  "Ning", "Niu", "Nong", "Nou", "Nu", "Nuan", "Nuo", "Nv", "Nve", "O", "Ou", "Pa", "Pai",
  "Pan", "Pang", "Pao", "Pei", "Pen", "Peng", "Pi", "Pian", "Piao", "Pie", "Pin", "Ping",
  "Po", "Pou", "Pu", "Qi", "Qia", "Qian", "Qiang", "Qiao", "Qie", "Qin", "Qing", "Qiong",
  "Qiu", "Qu", "Quan", "Que", "Qun", "Ran", "Rang", "Rao", "Re", "Ren", "Reng", "Ri",
  "Rong", "Rou", "Ru", "Ruan", "Rui", "Run", "Ruo", "Sa", "Sai", "San", "Sang", "Sao",
  "Se", "Sen", "Seng", "Sha", "Shai", "Shan", "Shang", "Shao", "She", "Shei", "Shen",
  "Sheng", "Shi", "Shou", "Shu", "Shua", "Shuai", "Shuan", "Shuang", "Shui", "Shun",
  "Shuo", "Si", "Song", "Sou", "Su", "Suan", "Sui", "Sun", "Suo", "Ta", "Tai", "Tan",
  "Tang", "Tao", "Te", "Teng", "Ti", "Tian", "Tiao", "Tie", "Ting", "Tong", "Tou", "Tu",
  "Tuan", "Tui", "Tun", "Tuo", "Wa", "Wai", "Wan", "Wang", "Wei", "Wen", "Weng", "Wo",
  "Wu", "Xi", "Xia", "Xian", "Xiang", "Xiao", "Xie", "Xin", "Xing", "Xiong", "Xiu", "Xu",
  "Xuan", "Xue", "Xun", "Ya", "Yan", "Yang", "Yao", "Ye", "Yi", "Yiao", "Yin", "Ying",
  "Yo", "Yong", "You", "Yu", "Yuan", "Yue", "Yun", "Za", "Zai", "Zan", "Zang", "Zao",
  "Ze", "Zei", "Zen", "Zeng", "Zha", "Zhai", "Zhan", "Zhang", "Zhao", "Zhe", "Zhei",
  "Zhen", "Zheng", "Zhi", "Zhong", "Zhou", "Zhu", "Zhua", "Zhuai", "Zhuan", "Zhuang",
  "Zhui", "Zhun", "Zhuo", "Zi", "Zong", "Zou", "Zu", "Zuan", "Zui", "Zun", "Zuo",
  "Dei","Eng","Hng","Lo","Lue","N","Qui","R","Tei"};
}
