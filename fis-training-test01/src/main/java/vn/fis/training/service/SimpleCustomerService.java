package vn.fis.training.service;

import vn.fis.training.domain.Customer;
import vn.fis.training.exception.ApplicationException;
import vn.fis.training.exception.CustomerNotFoundException;
import vn.fis.training.exception.DuplicateCustomerException;
import vn.fis.training.exception.InvalidCustomerException;
import vn.fis.training.store.InMemoryCustomerStore;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class SimpleCustomerService  implements CustomerService {

    private InMemoryCustomerStore customerStore;

//    ApplicationException error = new ApplicationException() {
//        @Override
//        public String getErrorCode() {
//            return null;
//        }
//    };
    @Override
    public Customer findById(String id) throws CustomerNotFoundException {
        if(isNullOrEmpty(id)) {
            throw new IllegalArgumentException("Khong the tim kiem voi Empty ID");
        }

        List<Customer> customerList = customerStore.findAll();
        for (int idx=0; idx<customerList.size();idx++){
            if (customerList.get(idx).getId() == id){
                return customerList.get(idx);
            }
        }
        throw new CustomerNotFoundException(String.format("Khong tim thay customer ",id));


        //TODO: Implement method tho dung dac ta cua CustomerService interface
    }

    private boolean isNullOrEmpty(String id) {
        if(id==null) return false;
        if(id.trim().length() == 0) return false;
        return true;
    }

    @Override
    public Customer createCustomer(Customer customer) throws InvalidCustomerException {
        String validateMobile = "0\\d{9,10}";
        String validateName = "\\w{5,50}";
        LocalDate now = LocalDate.now();
        Period actualAge= Period.between(customer.getBirthDay(),now);
        double age = actualAge.getYears();
        try{
            if (customer.getMobile().matches(validateMobile)&&customer.getName().matches(validateName)&&age>10){
                return customer;
            }
        }
        catch (InvalidCustomerException e){
            e.getErrorCode();
        }
        validate(customer);
        checkDuplicate(customer);
        return customerStore.insertOrUpdate(customer);
        //TODO: Implement method tho dung dac ta cua CustomerService interface
        //return null;
    }

    private void checkDuplicate(Customer customer) {
        if (customerStore.findAll().stream().filter(customer1 ->
            customer1.getMobile().equals(customer.getMobile())
        ).findFirst().isPresent()) {
            throw new DuplicateCustomerException(customer,String.format("Customer with phone number %s is duplicated ",customer.getMobile()));
        }
    }
    private void validate(Customer customer) {
        if (isNullOrEmpty(customer.getName())){
            throw new InvalidCustomerException(customer,"Customer name is empty");
        }
        if (isNullOrEmpty((customer.getMobile()))){
            throw new InvalidCustomerException(customer,"Mobile is empty");
        }
        //
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        validate(customer);
        findById(customer.getId());
        return customerStore.insertOrUpdate(customer);
        //TODO: Implement method tho dung dac ta cua CustomerService interfac
    }

    @Override
    public void deleteCustomerById(String id) {
        if(isNullOrEmpty(id)){
            throw new IllegalArgumentException("Can not delete empty id");
        }
        findById(id);
        customerStore.deleteById(id);
        //TODO: Implement method tho dung dac ta cua CustomerService interface
    }

    @Override
    public List<Customer> findAllOrderByNameAsc() {
        //TODO: Implement method tho dung dac ta cua CustomerService interface
        return customerStore.findAll().stream().sorted(Comparator.comparing(Customer::getName)).collect(Collectors.toList());
    }

    @Override
    public List<Customer> findAllOrderByNameLimit(int limit) {

        //TODO: Implement method tho dung dac ta cua CustomerService interface
        return customerStore.findAll().stream().sorted(Comparator.comparing(Customer::getName)).limit(limit).collect(Collectors.toList());

    }

    @Override
    public List<Customer> findAllCustomerByNameLikeOrderByNameAsc(String custName, String limit) {
        //TODO: Implement method tho dung dac ta cua CustomerService interface
        return null;
    }

    @Override
    public List<SummaryCustomerByAgeDTO> summaryCustomerByAgeOrderByAgeDesc() {
        //TODO: Implement method tho dung dac ta cua CustomerService interface
        return null;
    }

    public void setCustomerStore(InMemoryCustomerStore customerStore) {
        this.customerStore = customerStore;
    }
}
