plugins {
	id("net.neoforged.moddev") version "+"
//	id("me.modmuss50.mod-publish-plugin") version "+"
}
base.archivesName.set(p("mod_id"))
group = p("mod_group_id")
version = "${p("minecraft_version")}-${p("mod_version")}+${p("upper_loader")}"
java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))
tasks.jar { from("LICENSE") }
var generateMetadata = tasks.register<ProcessResources>("generateMetadata") {
	expand(properties.mapValues { it.value.toString() })
	from("src/main/templates")
	into("build/generated/sources/modMetadata")
}
var cleanMetadata = tasks.register<Delete>("cleanMetadata") {
	delete("build/generated/sources/modMetadata")
}
sourceSets.main.get().resources.srcDir(generateMetadata)
neoForge.ideSyncTasks.addAll(cleanMetadata, generateMetadata)
neoForge {
	version = p("loader_version")
	parchment {
		mappingsVersion.set(p("parchment_version"))
		minecraftVersion.set(p("minecraft_version"))
	}
	runs {
		create("client").client()
		configureEach {
			jvmArguments.add("-XX:+AllowEnhancedClassRedefinition")
			systemProperty("terminal.jline", "true")
		}
	}
	mods.create(p("mod_id")).sourceSet(sourceSets.main.get())
}
fun p(key: String) = property(key).toString()
