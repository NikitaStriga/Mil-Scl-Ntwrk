# A *REST API* for social network
### Project name - Mil-Scl-Ntwrk
### What was used - PostgreSQL + JPA(Hibernate) + DTO(converter - ModelMapper) + SpringBoot(Data + Rest + Security) + Mail + AWS + JWT + FLYWAY + SWAGGER + CACHE(Caffeine)
#### The project includes (main parts):
* ORM (vendor adapter - Hibernate), link: [ORM](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/tree/master/common/src/main/java/q3df/mil/entities)
* Dto (link: [DTO](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/tree/master/common/src/main/java/q3df/mil/dto). For conversion was used [ModeMapper](http://modelmapper.org/), impl in code: [mapper](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/tree/master/common/src/main/java/q3df/mil/mapper) )
* JPA (vendor adapter - Hibernate + covered by Spring Data JPA)
  * [repository](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/tree/master/common/src/main/java/q3df/mil/repository) (JPA wrappers + @Queries + | Pageable with Sort | )
  * [service](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/tree/master/common/src/main/java/q3df/mil/service) (most of the non-primitive logic locate in [userServiceImpl](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/blob/master/common/src/main/java/q3df/mil/service/impl/UserServiceImpl.java))
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
&ensp;Short description: ORM + DTO + JPA + Rest + Security + Mail + AWS + FLYWAY + SWAGGER + CACHE  
&ensp;What I would like to highlight in the project:
* optimizing queries using ***@org.hibernate.annotations.BatchSize***,
 ***@org.hibernate.annotations.Fetch(FetchMode.SUBSELECT)***, 
 as well as deleting  ***@org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)***
  ( with my "nesting" of entities, not having these annotations,
   my bd would just puff like that cartoon stove, actually a fragment of the cartoon - [watch how the stove puffs without SMS and registration](https://youtu.be/iJzEJ7Oa_oI?t=1007) )
* Criteria method on all properties with pagination
* well, and probably the most worthy part of the project, as it seems to me, is the implementation of the filter in security, which uses a modified JWT implementation.
And it is this bunch of Filter + JWT that allows you to secure the URL, as well as operations with data
 to which users should not have rights + implemented verification of the creation date of the token with the date of the password change.
 Benefits:
  * user 1, cannot read, for example, the dialogs of user 2 (in general, the check can be hung where you see fit)
  * the user cannot change, delete other people's posts, and also post not on his resource
  * if the token was compromised, then the user can change the password and then the token on the bad guy side will not be valid (implemented by checking through Claims and pChange in the User table, there is also a cache for this case Map <login, pChange>, so that do not constantly climb into the database, @PutCache is on the password change operation)
  * the request also records information about the user id and the presence of an admin role, but nevertheless, if you are an admin, then not all operations are available to you, but only deletion (it would probably be silly to allow users to post or change records)
  * for more information please visit: [JWT](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/blob/master/api/src/main/java/q3df/mil/security/util/TokenUtils.java), [filter](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/blob/master/api/src/main/java/q3df/mil/security/filter/JwtTokenFilter.java) and [permissionClass](https://github.com/NikitaStriga/Mil-Scl-Ntwrk/blob/master/api/src/main/java/q3df/mil/util/CustomPermission.java).
##### _© by Mil_




 
  
