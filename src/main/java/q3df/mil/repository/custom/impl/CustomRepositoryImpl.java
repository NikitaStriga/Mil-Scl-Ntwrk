package q3df.mil.repository.custom.impl;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import q3df.mil.dto.user.UserPreview;
import q3df.mil.entities.enums.Gender;
import q3df.mil.entities.user.User;
import q3df.mil.exception.CustomException;
import q3df.mil.mapper.user.UserPreviewMapper;
import q3df.mil.repository.custom.CustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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


    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<?> showCountOfUserByCityAndCountry() {
        final String COUNTRY = "country";
        final String CITY = "city";
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = cb.createQuery(Object[].class);
        Root<User> u = criteria.from(User.class);
        criteria.multiselect(u.get(COUNTRY), u.get(CITY), cb.count(u));
        criteria.groupBy(u.get(COUNTRY), u.get(CITY));
        criteria.orderBy(cb.asc(u.get(COUNTRY)), cb.asc(u.get(CITY)));
        List<Object[]> resultList = entityManager.createQuery(criteria).getResultList();
        List<SupStatisticClass> list = new ArrayList<>();
        for (Object[] objects : resultList) {
            list.add(new SupStatisticClass((String) objects[0], (String) objects[1], (Long) objects[2]));
        }
        return resultList;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<UserPreview> showUsersByParams(String... params) {
        //the first step is to count the number of records
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> from = criteriaQuery.from(User.class);
        criteriaQuery.select(criteriaBuilder.count(from));
        Long count = entityManager.createQuery(criteriaQuery).getSingleResult();

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
        criteria
                .select(userRoot)
                .where(
                        cb.and(
                                cb.between(
                                        userRoot.get("birthday"),
                                        forBetweenFirstDate, forBetweenSecondDate
                                ),
                                cb.like(
                                        cb.lower(userRoot.get("city")),
                                        cityParam
                                ),
                                cb.like(
                                        cb.lower(userRoot.get("country")),
                                        countryParam
                                ),
                                cb.like(
                                        cb.lower(userRoot.get("city")),
                                        cityParam
                                ),
                                cb.like(cb.lower(userRoot.get("firstName")),
                                        firstNameParam
                                ),
                                cb.like(cb.lower(userRoot.get("lastName")),
                                        lastNameParam
                                ),
                                userRoot.get("gender").in(genderParam),
                                cb.equal(
                                        userRoot.get("delete"),
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

            TypedQuery<User> query = entityManager.createQuery(criteria);
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
            if (!StringUtils.isBlank(params[7]) && Integer.parseInt(params[7]) > 1) {
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

            query.setFirstResult(firstResult).setMaxResults(maxResult);
            List<User> resultList = query.getResultList();

            return resultList
                    .stream()
                    .map(userPreviewMapper::toDto)
                    .collect(Collectors.toList());

        }catch (Exception ex){
            throw new CustomException("Invalid input parameters. " + ex.getMessage());
        }
    }


    @Data
    class SupStatisticClass {
        private final String country;
        private final String city;
        private final Long count;
    }
}
