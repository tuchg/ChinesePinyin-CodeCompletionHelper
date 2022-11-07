import com.github.tuchg.nonasciicodecompletionhelper.utils.countContainsSomeChar
import com.github.tuchg.nonasciicodecompletionhelper.utils.取多音字组合
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Assert
import pansong291.simplepinyin.Pinyin
import kotlin.system.measureTimeMillis

class PinyinExTests : BasePlatformTestCase() {

    fun `test @中文转拼音`() {
        Assert.assertArrayEquals(取多音字组合("测试变量"){Pinyin.toPinyin(it,Pinyin.LOW_CASE)}, arrayOf("ceshibianliang"))
        Assert.assertArrayEquals(取多音字组合("handle测试变量"){Pinyin.toPinyin(it,Pinyin.LOW_CASE)}, arrayOf("handleceshibianliang"))
        Assert.assertArrayEquals(取多音字组合("多重"){Pinyin.toPinyin(it,Pinyin.LOW_CASE)}, arrayOf("duozhong", "duochong"))
        Assert.assertArrayEquals(取多音字组合("W中文"){Pinyin.toPinyin(it,Pinyin.LOW_CASE)}, arrayOf("wzhongwen"))
        Assert.assertArrayEquals(取多音字组合("w中文"){Pinyin.toPinyin(it,Pinyin.LOW_CASE)}, arrayOf("wzhongwen"))
        Assert.assertArrayEquals(取多音字组合("中文W"){Pinyin.toPinyin(it,Pinyin.LOW_CASE)}, arrayOf("zhongwenw"))
        Assert.assertArrayEquals(取多音字组合("中W文"){Pinyin.toPinyin(it,Pinyin.LOW_CASE)}, arrayOf("zhongwwen"))
    }

    fun `test @拼音匹配统计`() {
        Assert.assertEquals(6, countContainsSomeChar("handleceshibianliang", "hceshi"))
        Assert.assertEquals(4, countContainsSomeChar("ceshibianliang", "csbl"))
        Assert.assertEquals(3, countContainsSomeChar("wzhongwen", "wzw"))
        Assert.assertEquals(3, countContainsSomeChar("zhongwenw", "zww"))
        Assert.assertEquals(3, countContainsSomeChar("zhongwwen", "zww"))
    }

    fun `test @笛卡尔积测试`() {
        val 耗时 = measureTimeMillis {
            repeat(1) {
                取多音字组合("啊嗯哦家"){ Pinyin.toPinyin(it, Pinyin.FIRST_UP_CASE)}
            }
        }
        println("耗时：$耗时 ms")
    }

    fun `test @计算字符串内包含需要的单个字符的数量`() {
        val 耗时 = measureTimeMillis {
            repeat(10000) {
                countContainsSomeChar("chijkdffd", "cjk")
            }
        }
        println("耗时：$耗时 ms")
    }
}