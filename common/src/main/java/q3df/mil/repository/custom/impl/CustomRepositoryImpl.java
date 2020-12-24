package q3df.mil.repository.custom.impl;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import q3df.mil.dto.user.UserPreview;
import q3df.mil.entities.enums.Gender;
import q3df.mil.entities.user.User;
import q3df.mil.entities.user.User_;
import q3df.mil.exception.CriteriaCustomException;
import q3df.mil.mapper.user.UserPreviewMapper;
import q3df.mil.repository.custom.CustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomRepositoryImpl implements CustomRepository {

    @PersistenceContext
    private EntityManager entityManager;
    private final UserPreviewMapper userPreviewMapper;

    @Autowired
    public CustomRepositoryImpl(UserPreviewMapper userPreviewMapper) {
        this.userPreviewMapper = userPreviewMapper;
    }


    /**
     * Show count of user in countries and cities
     * @return full statistics of registered users by country and city
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<?> showCountOfUserByCityAndCountry() {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = cb.createQuery(Object[].class);
        Root<User> u = criteria.from(User.class);

        //paths
        Path<String> countryPath = u.get(User_.country);
        Path<String> cityPath = u.get(User_.city);

        //define criteria query
        criteria
                .multiselect(
                        countryPath, cityPath, cb.count(u)
                )
                .groupBy(
                        countryPath, cityPath
                )
                .orderBy(
                        cb.asc(countryPath), cb.asc(cityPath)
                );


        //create query
        List<Object[]> resultList = entityManager.createQuery(criteria).getResultList();

        //parse result
        List<SupStatisticClass> list = new ArrayList<>();
        for (Object[] objects : resultList) {
            list.add(new SupStatisticClass((String) objects[0], (String) objects[1], (Long) objects[2]));
        }
        return resultList;
    }

    /**
     * Show result of searching users by params
     * improved 2nd implementation of the method
     * note: supports like 'foo%', but not support like '%...' cuz b-tree index doesnt work with it
     * @param params various params for search
     * @return a list of users that match the search parameters with pagination
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<UserPreview> showUsersByParams(String... params) {

        //max number of results
        final int maxResults = 50;

        //for concatenation in like operator
        final String defaultValue = "%";

        //the first step is to count the number of records
        Long count = countOfResults();

        //the second step is make pageable request to db
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class);

        //paths
        Path<LocalDate> localDatePath = userRoot.get(User_.birthday);
        Path<String> countryPath = userRoot.get(User_.country);
        Path<String> cityPath = userRoot.get(User_.city);
        Path<String> firstNamePath = userRoot.get(User_.firstName);
        Path<String> lastNamePath = userRoot.get(User_.lastName);
        Path<Gender> genderPath = userRoot.get(User_.gender);
        Path<Boolean> deletePath = userRoot.get(User_.delete);

        //list of predicates
        final List<Predicate> listOfPredicates = new ArrayList<>();

        //parse data
        try {

            //after Date
            LocalDate afterDate = null;
            if (!StringUtils.isBlank(params[0])) {
                afterDate = LocalDate.parse(params[0]);
                Predicate predicateAfterDate = cb.greaterThanOrEqualTo(localDatePath, afterDate);
                listOfPredicates.add(predicateAfterDate);
            }

            //before date
            LocalDate beforeDate = null;
            if (!StringUtils.isBlank(params[1])) {
                beforeDate = LocalDate.parse(params[1]);
                Predicate predicateBeforeDate = cb.lessThanOrEqualTo(localDatePath, beforeDate);
                listOfPredicates.add(predicateBeforeDate);
            }

            //if beforeDate>afterDate then delete predicates from list
            if (afterDate != null && beforeDate != null && afterDate.isAfter(beforeDate)) {
                listOfPredicates.clear();
            }

            //country
            String country;
            if (!StringUtils.isBlank(params[2])) {
                country = (params[2] + defaultValue).toLowerCase();
                listOfPredicates.add(cb.like(cb.lower(countryPath), country));
            }

            //city
            String city;
            if (!StringUtils.isBlank(params[3])) {
                city = (params[3] + defaultValue).toLowerCase();
                listOfPredicates.add(cb.like(cb.lower(cityPath), city));
            }

            //firstName
            String firstName;
            if (!StringUtils.isBlank(params[4])) {
                firstName = (params[4] + defaultValue).toLowerCase();
                listOfPredicates.add(cb.like(cb.lower(firstNamePath), firstName));
            }

            //lastName
            String lastName;
            if (!StringUtils.isBlank(params[5])) {
                lastName = (params[5] + defaultValue).toLowerCase();
                listOfPredicates.add(cb.like(cb.lower(lastNamePath), lastName));
            }

            //gender
            if (!StringUtils.isBlank(params[6])) {
                try {
                    Gender gender = Gender.valueOf(params[6].toUpperCase());
                    listOfPredicates.add(cb.equal(genderPath, gender));
                } catch (IllegalArgumentException exception) {
                    //just a stub
                    //if in params will be bad Gender we just skip it
                }
            }

            //only users who's not deleted
            listOfPredicates.add(cb.equal(deletePath, false));

            //define criteria query
            criteria
                    .select(userRoot)
                    .where(
                            cb.and(
                                    listOfPredicates.toArray(Predicate[]::new)
                            )
                    );

            //get query
            TypedQuery<User> query = entityManager.createQuery(criteria);

            //determine the page and the number of returned results
            int countOfAvailableRecords = count.intValue();

            //number of page
            int numberOfPage = 1;
            if (!StringUtils.isBlank(params[7])
                    && Integer.parseInt(params[7]) > 1) {
                numberOfPage = Integer.parseInt(params[7]);
            }

            //number of result, if greater than 50 the value will be set to the maximum number of results
            int numberOfResults = 10;
            if (!StringUtils.isBlank(params[8])
                    && Integer.parseInt(params[8]) > 0
                    && Integer.parseInt(params[8]) <= maxResults) {
                numberOfResults = Integer.parseInt(params[8]);
            }

            //define values of firstResult and maxResult which the final query needs for pagination
            int firstResult = numberOfPage == 1 ? 0 : (numberOfPage - 1) * numberOfResults - 1;
            int maxResult = numberOfResults;
            if (countOfAvailableRecords <= firstResult + 1) {
                numberOfPage = countOfAvailableRecords / numberOfResults;
                firstResult = (numberOfPage - 1) * numberOfResults - 1;
            }

            //set pagination
            query.setFirstResult(firstResult).setMaxResults(maxResult);

            //get result
            List<User> resultList = query.getResultList();

            return resultList
                    .stream()
                    .map(userPreviewMapper::toDto)
                    .collect(Collectors.toList());

        } catch (Exception ex) {
            throw new CriteriaCustomException("Invalid input parameters in search  criteria method " + ex.getMessage());
        }
    }

    /**
     * Counts users with param delete = false
     * @return count of available users
     */
    @Override
    public Long countOfResults() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> from = criteriaQuery.from(User.class);
        criteriaQuery
                .select(criteriaBuilder.count(from))
                .where(
                        criteriaBuilder.equal(from.get(User_.delete),
                                false)
                );
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }


    /**
     * just sup class...
     */
    @Data
    class SupStatisticClass {
        private final String country;
        private final String city;
        private final Long count;
    }


    /**
     * Show result of searching users by params
     * 1st implementation  which was replaced by the 2nd implementation
     * check ---> public List<UserPreview> showUsersByParams(String... params)
     * @param params searching params
     * @return list of users
     */
    /*
    public List<UserPreview> showUsersBySearchingParamsFirstImplementation(String... params){
        //the first step is to count the number of records
        Long count = countOfResults();

        //the second step is make pageable request to db
        final String firstDefaultDate = "1900-01-01";
        final String secondDefaultDate = "2100-01-01";
        final String defaultValue = "%";
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class);

        ParameterExpression<LocalDate> forBetweenFirstDate = cb.parameter(LocalDate.class);
        ParameterExpression<LocalDate> forBetweenSecondDate = cb.parameter(LocalDate.class);
        ParameterExpression<String> cityParam = cb.parameter(String.class);
        ParameterExpression<String> countryParam = cb.parameter(String.class);
        ParameterExpression<String> firstNameParam = cb.parameter(String.class);
        ParameterExpression<String> lastNameParam = cb.parameter(String.class);
        ParameterExpression<List> genderParam = cb.parameter(List.class);

        Path<LocalDate> localDatePath = userRoot.get(User_.birthday);
        Path<String> countryPath = userRoot.get(User_.country);
        Path<String> cityPath = userRoot.get(User_.city);
        Path<String> firstNamePath = userRoot.get(User_.firstName);
        Path<String> lastNamePath = userRoot.get(User_.lastName);
        Path<Gender> genderPath = userRoot.get(User_.gender);
        Path<Boolean> deletePath = userRoot.get(User_.delete);


        criteria
                .select(userRoot)
                .where(
                        cb.and(
                                cb.between(
                                        localDatePath,
                                        forBetweenFirstDate, forBetweenSecondDate
                                ),
                                cb.like(
                                        countryPath,
                                        countryParam
                                ),
                                cb.like(
                                        cityPath,
                                        cityParam
                                ),
                                cb.like(
                                        firstNamePath,
                                        firstNameParam
                                ),
                                cb.like(
                                        lastNamePath,
                                        lastNameParam
                                ),
                                genderPath.in(genderParam),
                                cb.equal(
                                        deletePath,
                                        false
                                )
                        )
                );

        try {
            //parse data
            LocalDate firstDate = LocalDate.parse(firstDefaultDate);
            if (!StringUtils.isBlank(params[0])) {
                firstDate = LocalDate.parse(params[0]);
            }
            LocalDate secondDate = LocalDate.parse(secondDefaultDate);
            if (!StringUtils.isBlank(params[1])) {
                secondDate = LocalDate.parse(params[1]);
            }

            String country = defaultValue;
            if (!StringUtils.isBlank(params[2])) {
                country = (defaultValue + params[2] + defaultValue).toLowerCase();
            }

            String city = defaultValue;
            if (!StringUtils.isBlank(params[3])) {
                city = (defaultValue + params[3] + defaultValue).toLowerCase();
            }

            String firstName = defaultValue;
            if (!StringUtils.isBlank(params[4])) {
                firstName = (defaultValue + params[4] + defaultValue).toLowerCase();
            }

            String lastName = defaultValue;
            if (!StringUtils.isBlank(params[5])) {
                lastName = (defaultValue + params[5] + defaultValue).toLowerCase();
            }

            List<Gender> listGender = new ArrayList<>();
            if (!StringUtils.isBlank(params[6])) {
                try{
                    listGender.add(Gender.valueOf(params[6].toUpperCase()));
                }catch (IllegalArgumentException exception){
                    listGender.addAll(Arrays.asList(Gender.values()));
                }
            } else {
                listGender.addAll(Arrays.asList(Gender.values()));
            }

            //get query
            TypedQuery<User> query = entityManager.createQuery(criteria);
            //set params
            query.setParameter(forBetweenFirstDate, firstDate)
                    .setParameter(forBetweenSecondDate, secondDate)
                    .setParameter(countryParam, country)
                    .setParameter(cityParam, city)
                    .setParameter(firstNameParam, firstName)
                    .setParameter(lastNameParam, lastName)
                    .setParameter(genderParam, listGender);

            //determine the page and the number of returned results
            int countOfAvailableRecords = count.intValue();

            int numberOfPage = 1;
            if (!StringUtils.isBlank(params[7])
                    && Integer.parseInt(params[7]) > 1) {
                numberOfPage = Integer.parseInt(params[7]);
            }

            int numberOfResults = 10;
            if (!StringUtils.isBlank(params[8])
                    && Integer.parseInt(params[8]) > 0
                    && Integer.parseInt(params[8]) <= 50) {
                numberOfResults = Integer.parseInt(params[8]);
            }

            int firstResult = numberOfPage == 1 ? 0 : (numberOfPage - 1) * numberOfResults - 1;
            int maxResult = numberOfResults;
            if (countOfAvailableRecords <= firstResult + 1) {
                numberOfPage = countOfAvailableRecords / numberOfResults;
                firstResult = (numberOfPage - 1) * numberOfResults - 1;
            }

            //set pagination
            query.setFirstResult(firstResult).setMaxResults(maxResult);
            //get result
            List<User> resultList = query.getResultList();

            return resultList
                    .stream()
                    .map(userPreviewMapper::toDto)
                    .collect(Collectors.toList());

        }catch (Exception ex){
            throw new CustomException("Invalid input parameters. " + ex.getMessage());
        }
    }
     */
}
