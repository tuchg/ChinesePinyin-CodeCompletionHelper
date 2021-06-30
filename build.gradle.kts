import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Java support
    id("java")
    // Kotlin support
    id("org.jetbrains.kotlin.jvm") version "1.5.10"
    // gradle-intellij-plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
    id("org.jetbrains.intellij") version "1.0"
    // gradle-changelog-plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
    id("org.jetbrains.changelog") version "1.1.2"
}

fun properties(key: String) = project.findProperty(key).toString()

group = properties("pluginGroup")
version = properties("pluginVersion")

// Configure project's dependencies
repositories {
    maven { setUrl("https://www.jetbrains.com/intellij-repository/releases/") }
    maven { setUrl("https://www.jetbrains.com/intellij-repository/snapshots/") }
    maven { setUrl("https://maven.aliyun.com/repository/public/") }
    mavenLocal()
    mavenCentral()
}
dependencies {
}

// Configure gradle-intellij-plugin plugin.
// Read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    pluginName.set(properties("pluginName"))
    version.set(properties("platformVersion"))
    type.set(properties("platformType"))
    downloadSources.set(properties("platformDownloadSources").toBoolean())
    updateSinceUntilBuild.set(true)
// 运行平台
//    localPath = customIdeDir
//    localSourcesPath = customIdeDir

//  Plugin Dependencies:
//  https://www.jetbrains.org/intellij/sdk/docs/basics/plugin_structure/plugin_dependencies.html
    // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file.
    plugins.set(properties("platformPlugins").split(',').map(String::trim).filter(String::isNotEmpty))
}

// Configure gradle-changelog-plugin plugin.
// Read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    version = properties("pluginVersion")
    groups = emptyList()
}

tasks {
    // Set the compatibility versions to 1.8
    withType<JavaCompile> {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    patchPluginXml {
        version.set(properties("pluginVersion"))
        sinceBuild.set(properties("pluginSinceBuild"))
        untilBuild.set(properties("pluginUntilBuild"))
        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        pluginDescription.set(
            File(projectDir, "README.md").readText().lines().run {
                val start = "<!-- Plugin description -->"
                val end = "<!-- Plugin description end -->"

                if (!containsAll(listOf(start, end))) {
                    throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end))
            }.joinToString("\n").run {
                val last = this.replaceFirst(
                    "<!-- E -->",
                    // language=HTML
                    """
<h3>English</h3>

> The original intention is that for some businesses that are not suitable for expression in English, you can use a straightforward native language instead of pinyin, and then call what is expressed in your native language as you usually write code to solve some naming difficulties 🤔

Let your IDE support Chinese programming, and enjoy the Chinese intelligent coding experience consistent with the English environment

* Input pinyin completion. Chinese identifier will be shown below. Input `zw` and prompt 中文 [**Z**hong **W**en].
* Polyphonic words complete without difference
* Support all identifiers, including variable names, function names, and class names

Compatible with all the Intellij Platform product, indiscriminate Chinese programming, Java, Kotlin, JavaScript, C#，Golang, or Haskell all support...

> 为啥这里会有一段不符合插件受众的别扭英文介绍💬  <a href="https://github.com/tuchg/ChinesePinyin-CodeCompletionHelper/issues/5">#issues-5</a>

<h3>中文</h3>
"""
                )
                markdownToHTML(last)
            }
        )

        // Get the latest available change notes from the changelog file
        changeNotes.set(provider { changelog.getLatest().toHTML() })
    }
    runPluginVerifier {
        ideVersions.set(properties("pluginVerifierIdeVersions").split(',').map(String::trim).filter(String::isNotEmpty))
    }
    publishPlugin {
        dependsOn("patchChangelog")
        token.set(System.getenv("PUBLISH_TOKEN"))
        // pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
        // https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
        channels.set(listOf(properties("pluginVersion").split('-').getOrElse(1) { "default" }.split('.').first()))
    }
}
