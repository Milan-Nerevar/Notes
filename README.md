# Notes

Sample app using standard Android platform components.

Backbone structure of the app consists of DI framework (Koin), ViewModel (by Google) and Navigation library (by Google).

Network is handled by OkHttp & Retrofit (with Gson parsing). Since the work took me 4 and a half hours, which is a lot, I consider implementation of network error states not mandatory.

Note: Application was not built by TDD 

Note: API is simple and does not do anything, therefore you can only retrieve basic list of items. Saving/updating/creating does not really work.
