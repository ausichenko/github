# GitHub
GitHub Search android application

## Specs
- Minimum **SDK 16**
- Target **SDK 28**
- [Kotlin](https://kotlinlang.org/) language
- [AndroidX](https://developer.android.com/jetpack/androidx) is the open-source project that the Android team uses to develop, test, package, version and release libraries within Jetpack.
- [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) with [Clean architecture](http://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Android architecture components](https://developer.android.com/topic/libraries/architecture):
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) is an observable data holder class
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) for store and manage UI-related data in a lifecycle conscious way
  - [Room](https://developer.android.com/topic/libraries/architecture/room) is an a SQLite object mapping library
- [Material components](https://github.com/material-components/material-components-android) a drop-in replacement for Android's Design Support Library
- [KOIN](https://insert-koin.io) a pragmatic lightweight dependency injection framework for Kotlin 
- [RxJava](https://github.com/ReactiveX/RxJava) a library for composing asynchronous and event-based programs using observable sequences for the Java VM.
- [RxAndroid](https://github.com/ReactiveX/RxAndroid) Reactive Extensions for Android
- [OkHttp](http://square.github.io/okhttp) an HTTP & HTTP/2 client for Android and Java applications
- [Retrofit](https://square.github.io/retrofit) a type-safe HTTP client for Android and Java
- [Gson](https://github.com/google/gson) a library that can be used to convert Objects into their JSON representation
- [Picasso](http://square.github.io/picasso) a powerful image downloading and caching library for Android
- [Stetho](http://facebook.github.io/stetho) a debug bridge for Android applications

## Ideas
Also this project has examples of code which I deleted from main branch. This ideas are good, but it is not suitable for the current project. Maybe somewhere it will be useful.  
I left these examples in the following branches:
- **feature/cache-repository** - Caching searched repositories with search query
- **feature/databinding** - Search activity databinding and fragments
- **feature/databinding2** - Example with changing state: loading, success, error

## Codestyle
Don't worry about it.  
In the project enabled [Kotlin code style](https://kotlinlang.org/docs/reference/coding-conventions.html) conventions.  
Not [Intellij IDEA Code Style Kotlin](https://www.jetbrains.com/help/idea/code-style-kotlin.html) (enable by default)

## Contribution
You find bug? Or have any ideas?  
Please contribute to the project either by [creating a PR](https://github.com/ausichenko/github/compare) or [submitting an issue](https://github.com/ausichenko/github/issues/new) on GitHub.

## Screenshots
| Search list | Offline state |
| ------ | ------ |
| ![](https://github.com/ausichenko/github/blob/master/.github/assets/search_list.jpg) | ![](https://github.com/ausichenko/github/blob/master/.github/assets/offline_state.jpg) |

## License
Licensed under the [GPL-3.0](https://www.gnu.org/licenses/gpl.html) license.  
(See the [LICENSE](https://github.com/ausichenko/github/blob/master/LICENSE) file for the whole license text.)
