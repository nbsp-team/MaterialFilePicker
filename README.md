# Material File Picker [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-MaterialFilePicker-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2690) 
Material file picker library for Android

![](https://i.imgur.com/mjxs05n.png)

## Using

Add repository url and dependency in application module gradle file:

```gradle
repositories {
    maven {
        url  "http://dl.bintray.com/lukaville/maven" 
    }
}

dependencies {
    compile 'com.nbsp:library:1.8'
}
```

Open file picker:

```java
new MaterialFilePicker()
    .withActivity(this)
    .withRequestCode(1)
    .withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
    .withFilterDirectories(true) // Set directories filterable (false by default)
    .withHiddenFiles(true) // Show hidden files and folders
    .start();
```
or
```java
Intent intent = new Intent(this, FilePickerActivity.class);
intent.putExtra(FilePickerActivity.ARG_FILE_FILTER, Pattern.compile(".*\\.txt$"));
intent.putExtra(FilePickerActivity.ARG_DIRECTORIES_FILTER, true);
intent.putExtra(FilePickerActivity.ARG_SHOW_HIDDEN, true);
startActivityForResult(intent, 1);
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
