# utils-android
[![Download](https://api.bintray.com/packages/interactiveservices/maven/utils-android/images/download.svg)](https://bintray.com/interactiveservices/maven/utils-android/_latestVersion)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://opensource.org/licenses/MIT)
[![API](https://img.shields.io/badge/API-14%2B-green.svg)](https://developer.android.com/about/versions/android-4.0.html)

## Утилитарные классы

- `AnimUtils` - вращение, прозрачность, сжатие View;
- `BitmapUtils` - полезные функции для работы c Bitmap;
- `EncryptUtils` - md5, sha1, base64 стандартный набор для работы
- `FontUtils` - функции для работы со шрифтами, Typeface и знаком рубля;
- `GrammarUtils` - формирует строку для часто встречающихся случаев в нужном падеже, например "8 фотограф*ий*";
- `IntentUtils` - для частых работ с Intent. Обрабатывает некорректные ситуации вроде отсутствия приложения для выполнения Intent;
- `MoneyUtils` - работа с формированием строк для денежных сумм, например: "1 800 ₽"
- `ResUtils` - работа с ресурсами, преобразование dp в пиксели и обратно
- `UiUtils` - работа с клавиатурой
- `ValidateUtils` - проверка на валидность email и номера карты

## Подключение библиотеки

Gradle plugin до 3.0.0
```groovy
dependencies {
    ...
    compile "su.ias.components:utils-android:$lastVersion"
    compile "com.android.support:exifinterface:$SupportVersion"
}
```
Gradle plugin 3.0.0+
```groovy
dependencies {
    ...
    implementation "su.ias.components:utils-android:$lastVersion"
    implementation "com.android.support:exifinterface:$SupportVersion"
}
```
## Fragment


### FragmentBuilder

небольшая билбиотека для облегчения работы с фрагментами, есть простые анимаци

Простой пример замены фрагмента
```java
    new FragmentBuilde(R.id.container, new Fragment(), getSupportFragmentMamanger())
        .addStringArgument("title", "string params")
        .setAnimation(new SlideLeftToRight())
        .addToBackStack(true)
        .replace();

```

### FragmentUtils

