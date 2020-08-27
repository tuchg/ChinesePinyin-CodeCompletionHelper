import com.github.tuchg.nonasciicodecompletionhelper.utils.countContainsSomeChar
import com.github.tuchg.nonasciicodecompletionhelper.utils.toPinyin
import com.intellij.testFramework.fixtures.CompletionAutoPopupTestCase
import pansong291.simplepinyin.Pinyin
import kotlin.system.measureTimeMillis


/**
 * @author: tuchg
 * @date: 2020/8/27 16:42
 * @description:
 */
class CompletionTests : CompletionAutoPopupTestCase() {
    /**
     * 指定测试数据目录
     */
    override fun getTestDataPath(): String? {
        return "src/test/testData"
    }

    fun `test @补全功能性测试`() {
        //todo
        myFixture.testCompletionVariants("", "han")
    }

    fun `test @补全核心正确性性测试`() {
        val original = "handle测试嗯"
        val input = "hceshi"

        toPinyin(original, Pinyin.LOW_CASE).forEach { pinyin ->
            println("$input 于 $pinyin 计为：${countContainsSomeChar(pinyin, input)}")
        }
    }

    fun `test @笛卡尔积测试`() {
        val costTime = measureTimeMillis {
            repeat(100000) {
                toPinyin("啊嗯哦家", Pinyin.FIRST_UP_CASE)
//                        .forEach { println(it) }
            }
        }
        println("耗时：$costTime ms")
    }

    fun `test @计算字符串内包含需要的单个字符的数量`() {
        val costTime = measureTimeMillis {
            repeat(10000) {
                countContainsSomeChar("chijkdffd", "cjk")
            }
        }
        println("耗时：$costTime ms")
    }
}