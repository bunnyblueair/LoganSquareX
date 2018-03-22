
通过 @BuildSubPackage(targetPkg = Constants.LOADER_PACKAGE_NAME,targetClass = "JsonMapperLoaderImpl",autoMerge = false)
注解自动产生map列表
lib 的automerge为false，这样的话生成一个类将映射搞进去
主成功的automerge==true 根据注解处理，将之前生成的类合并