# A *REST API* for social network
### Project name - Mil-Scl-Ntwrk
#### The project includes (main parts):
* ORM (vendor adapter - Hibernate), link: [ORM](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/tree/master/common/src/main/java/q3df/mil/entities)
* Dto (link: [DTO](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/tree/master/common/src/main/java/q3df/mil/dto). For conversion was used [ModeMapper](http://modelmapper.org/), impl in code: [mapper](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/tree/master/common/src/main/java/q3df/mil/mapper) )
* JPA (vendor adapter - Hibernate + covered by Spring Data JPA)
  * [repository](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/tree/master/common/src/main/java/q3df/mil/repository) (JPA wrappers + @Queries + | Pageable with Sort | )
  * [service](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/tree/master/common/src/main/java/q3df/mil/service)
  * [customRepository](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/blob/master/common/src/main/java/q3df/mil/repository/custom/impl/CustomRepositoryImpl.java) (written using Criteria) 
* Controllers
  * [controllers](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/tree/master/api/src/main/java/q3df/mil/controller)
  * [exception handler](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/blob/master/api/src/main/java/q3df/mil/controller/exception/GlobalExceptionHandler.java) (global exception handler)
* Security
  * [config](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/blob/master/api/src/main/java/q3df/mil/security/configuration/WebSecurityConfiguration.java)
  * [security controllers](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/tree/master/api/src/main/java/q3df/mil/security/controller) (several controllers responsible for application security)
  * [exception handling](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/tree/master/api/src/main/java/q3df/mil/security/exceptionhandling) (handler of authenticate and authorization exceptions )
  * [custom filter](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/blob/master/api/src/main/java/q3df/mil/security/filter/JwtTokenFilter.java) (a filter containing the logic for checking the token, as well as for the possibility of refreshing it and for writing certain parameters in request that are necessary for checking permission)
  * [userDetails](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/blob/master/api/src/main/java/q3df/mil/security/service/UserServiceProvider.java) (impl of UserDetailsService)
  * [JWT](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/blob/master/api/src/main/java/q3df/mil/security/util/TokenUtils.java) (impl of JWT logic)
##### another parts:
* [AWS](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/tree/master/common/src/main/java/q3df/mil/amazon) (saving photo to AWS)
* [MAIL](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/tree/master/common/src/main/java/q3df/mil/mail) (sending message to user mail for recovery password)
* [AOP](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/blob/master/api/src/main/java/q3df/mil/aop/LoggingAspect.java) (just for logging in exception handler)
* [customValidation](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/tree/master/common/src/main/java/q3df/mil/validators) (used in date fields)
* [permissionClass](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/blob/master/api/src/main/java/q3df/mil/util/CustomPermission.java) (used in controllers to check permission, to understand how it works check filter+jwt above)
* [cache](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/blob/master/common/src/main/java/q3df/mil/config/CaffeineConfig.java) (used in userService in next methods: findById and in logic for checking date of creation of JWT token  and when user change password  )
* [flyway](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/tree/master/common/src/main/resources/fway/migration) (migration)
##### pom.xmls:
* [main](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/blob/master/pom.xml)
  * [api](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/blob/master/api/pom.xml)
  * [common](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/tree/master/common)
##### main application.properties: [props](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/blob/master/api/src/main/resources/application.properties)  
##### From the author:
&ensp;Краткое описание: ORM + DTO + JPA + Rest + Security + Mail + AWS + FLYWAY + SWAGGER + CACHE  
Чтобы хотелось выделить в проекте:
* оптимизация запросов с помощью ***@org.hibernate.annotations.BatchSize***,
 ***@org.hibernate.annotations.Fetch(FetchMode.SUBSELECT)***, 
 а так же удаления  ***@org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)***
  ( с моей "вложенностью" сущностей, не имея этих аннотаций,
   моя бд бы просто пыхтела как та печка из мультфильма, собственно фрагмент мульта - [смотреть как пыхтит печка без смс и регистрации](https://youtu.be/iJzEJ7Oa_oI?t=1007) )
* метод Criteria по всем свойствам с пагинацией
* ну и наверное самой достойной частью проекта, как мне кажется, является реализация фильтра  в security, который использует измененную реализацию JWT. 
И именно эта связка Filter + JWT позволяет обезапасить URL, а так же операции с данными
 к которым у пользователей не должно быть прав + реализована проверка даты создания токена с датой изменения пароля.
 Преимущества:
  * пользователь 1, не может читать например диалоги пользователя 2 (в общем проверку можно повесить там, где вы посчитаете нужным)
  * пользователь не может изменять, удалять чужие записи, а также постить не на своем ресурсе
  * если токен был скомпроментирован, то пользователь может изменить пароль и тогда, токен на стороне bad guy не будет валиден (реализуется за счет проверки через Claims и pChange в таблице User, так же есть кеш для этого дела Map<UserID,PChange>, чтобы в бд постоянно не лезть, @PutCache стоит на операции изменения пароля)
  * так же в request записывается информация о id пользователя и наличие админ роли, но все же , если вы админ, то вам доступны не все операции, а только удаление (наверное глупо было бы разрешать постить или изменять записи у пользователей)
  * за более подробной информаацией необходимо посетить: [JWT](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/blob/master/api/src/main/java/q3df/mil/security/util/TokenUtils.java), [filter](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/blob/master/api/src/main/java/q3df/mil/security/filter/JwtTokenFilter.java) and [permissionClass](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/blob/master/api/src/main/java/q3df/mil/util/CustomPermission.java).
##### _© by Mil_




 
  
