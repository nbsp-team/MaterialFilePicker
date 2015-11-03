# Material File Picker
Material file picker library for Android

![](https://i.imgur.com/ibcVjpm.png)

## Using

Add dependency in application module gradle file:

```gradle
dependencies {
    compile 'com.nbsp:library:1.01'
}
```

Open file picker:

```java
Intent intent = new Intent(this, com.nbsp.materialfilepicker.ui.FilePickerActivity.class);
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
