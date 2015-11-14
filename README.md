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
    compile 'com.nbsp:library:1.02'
}
```

Open file picker:

```java
Intent intent = new Intent(this, FilePickerActivity.class);
startActivityForResult(intent, 1);
```

Ovveride on activity result:

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
