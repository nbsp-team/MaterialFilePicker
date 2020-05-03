# Material File Picker [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-MaterialFilePicker-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2690) [![Download](https://api.bintray.com/packages/lukaville/maven/materialfilepicker/images/download.svg?version=1.9.1) ](https://bintray.com/lukaville/maven/materialfilepicker/1.9.1/link)
Material file picker library for Android

![](https://i.imgur.com/mjxs05n.png)

## Using

Add repository url and dependency in application module gradle file:

```gradle
repositories {
    jcenter()
}

dependencies {
    implementation 'com.nbsp:materialfilepicker:1.9.1'
}
```

Open file picker:

```java
MaterialFilePicker()
    // Pass a source of context. Can be:
    //    .withActivity(Activity activity)
    //    .withFragment(Fragment fragment)
    //    .withSupportFragment(androidx.fragment.app.Fragment fragment)
    .withActivity(this)
    // With cross icon on the right side of toolbar for closing picker straight away
    .withCloseMenu(true)
    // Entry point path (user will start from it)
    .withPath(alarmsFolder.absolutePath)
    // Root path (user won't be able to come higher than it)
    .withRootPath(externalStorage.absolutePath)
    // Showing hidden files
    .withHiddenFiles(true)
    // Want to choose only jpg images
    .withFilter(Pattern.compile(".*\\.(jpg|jpeg)$"))
    // Don't apply filter to directories names
    .withFilterDirectories(false)
    .withTitle("Sample title")
    .withRequestCode(FILE_PICKER_REQUEST_CODE)
    .start()
```

Override on activity result:

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == 1 && resultCode == RESULT_OK) {
        String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
        // Do anything with file
    }
}
```

Runtime permissions:

You should handle runtime permissions in activity, from what you called Material File Picker.
Look [here](https://github.com/nbsp-team/MaterialFilePicker/blob/master/app/src/main/java/com/dimorinny/sample/MainActivity.java#L38-L69) for example code.

## Third Party Bindings

### React Native
You may now use this library with [React Native](https://github.com/facebook/react-native) via the module [here](https://github.com/prscX/react-native-file-selector)
