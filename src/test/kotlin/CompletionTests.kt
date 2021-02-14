import com.intellij.testFramework.fixtures.CompletionAutoPopupTestCase
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
        Assert.assertEquals(1, 补全项.size)
        Assert.assertEquals("方法", 补全项.get(0).lookupString)
    }

    fun `test @Java输入错误后无补全`() {
        myFixture.configureByText("Test.java", "class a { int 方法() { <caret> } }")
        myFixture.type("fo")
        val 补全项 = myFixture.completeBasic()
        Assert.assertNull("补全项应为空", 补全项)
    }

    fun `test @Kotlin中文补全成功但有重复`() {
        myFixture.configureByText("Test.kt", "class a { fun 方法(): Int { <caret> } }")
        myFixture.type("fang")
        val 补全项 = myFixture.completeBasic()
        Assert.assertTrue("补全项不为空", 补全项.isNotEmpty())
        Assert.assertEquals(2, 补全项.size)
        Assert.assertEquals("方法", 补全项.get(0).lookupString)
        Assert.assertEquals("方法", 补全项.get(1).lookupString)
    }
}