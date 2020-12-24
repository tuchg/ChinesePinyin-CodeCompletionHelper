import com.github.tuchg.nonasciicodecompletionhelper.utils.countContainsSomeChar
import com.github.tuchg.nonasciicodecompletionhelper.utils.toPinyin
import com.google.wireless.android.sdk.stats.IntellijProjectSizeStats
import com.intellij.testFramework.fixtures.CompletionAutoPopupTestCase
import pansong291.simplepinyin.Pinyin
import kotlin.system.measureTimeMillis
import org.junit.Assert


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
        //myFixture.testCompletionVariants("", "han")
    }

    fun `test @Java英文命名补全无效`() {
        myFixture.configureByText("Test.java", "class a { int method() { <caret> } }")
        myFixture.type("me")
        val 补全项 = myFixture.completeBasic()
        Assert.assertNull("补全项应为空", 补全项)
    }

    fun `test @Java补全成功`() {
        myFixture.configureByText("Test.java", "class a { int 方法() { <caret> } }")
        myFixture.type("fang")
        val 补全项 = myFixture.completeBasic()
        Assert.assertTrue("补全项不应为空", 补全项.isNotEmpty())
        Assert.assertEquals(2, 补全项.size)
        Assert.assertEquals("方法", 补全项.get(0).lookupString)
        Assert.assertEquals("方法", 补全项.get(1).lookupString)
    }

    fun `test @Java输入错误后无补全`() {
        myFixture.configureByText("Test.java", "class a { int 方法() { <caret> } }")
        myFixture.type("fo")
        val 补全项 = myFixture.completeBasic()
        Assert.assertTrue("补全项应为空", 补全项.isEmpty())
    }

    fun `test @Kotlin补全成功`() {
        myFixture.configureByText("Test.kt", "class a { fun 方法(): Int { <caret> } }")
        myFixture.type("fang")
        val 补全项 = myFixture.completeBasic()
        Assert.assertTrue("补全项不应为空", 补全项.isNotEmpty())
        Assert.assertEquals(2, 补全项.size)
        Assert.assertEquals("方法", 补全项.get(0).lookupString)
        Assert.assertEquals("方法", 补全项.get(1).lookupString)
    }

    fun `test @补全核心正确性测试`() {
        val 标识符 = "handle测试嗯"
        val 输入 = "hceshi"

        toPinyin(标识符, Pinyin.LOW_CASE).forEach { 拼音 ->
            Assert.assertEquals(6, countContainsSomeChar(拼音, 输入))
        }
    }

    fun `test @笛卡尔积测试`() {
        val 耗时 = measureTimeMillis {
            repeat(100000) {
                toPinyin("啊嗯哦家", Pinyin.FIRST_UP_CASE)
//                        .forEach { println(it) }
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