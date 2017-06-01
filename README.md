# utils-android
[ ![Download](https://api.bintray.com/packages/interactiveservices/maven/utils-android/images/download.svg) ](https://bintray.com/interactiveservices/maven/utils-android/_latestVersion)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://opensource.org/licenses/MIT)
[![API](https://img.shields.io/badge/API-14%2B-green.svg)](https://developer.android.com/about/versions/android-4.0.html)

## Утилитарные классы

- `AnimUtils` - вращение, прозрачность, сжатие View;
- `BitmapUtils` - полезные функции для работы c Bitmap;
- `FontUtils` - функции для работы со шрифтами, Typeface и знаком рубля;
- `GrammarUtils` - формирует строку для часто встречающихся случаев в нужном падеже, например "8 фотограф*ий*";
- `IntentUtils` - для частых работ с Intent. Обрабатывает некорректные ситуации вроде отсутствия приложения для выполнения Intent;
- `MoneyUtils` - работа с формированием строк для денежных сумм, например: "1 800 ₽"
- `UiUtils` - работа с клавиатурой и преобразование dp в пиксели и обратно

## Подключение библиотеки

```groovy
dependencies {
    ...
    compile 'su.ias.components:utils-android:1.0.3'
}
```
