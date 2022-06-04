package vn.fis.training.store;

import vn.fis.training.domain.Customer;
import vn.fis.training.exception.CustomerNotFoundException;
import vn.fis.training.exception.DuplicateCustomerException;
import vn.fis.training.service.SimpleCustomerService;

import java.util.*;

public final class InMemoryCustomerStore {
    /**
     *  Map dung de lu tru customer trong he thong
     */
    private final Map<String, Customer> mapCustomer = new HashMap<>();

    /**
     * @param customer doi tuong customer (da duoc chuan hoa truoc do)
     * @return Customer: Doi tuong customer sau khi da duoc luu thanh cong vao he thong
     * @throws vn.fis.training.exception.ApplicationException tuong ung.
     */
    public Customer insertOrUpdate(Customer customer) throws DuplicateCustomerException {
        try{
            if (!mapCustomer.containsKey(customer.getId())){
                SimpleCustomerService temp = new SimpleCustomerService();
                customer = temp.createCustomer(customer);
                mapCustomer.put(customer.getId(), customer);
                //TODO: Implement theo dac ta
                return  customer;
            }
        }
        catch (DuplicateCustomerException e){
            e.getErrorCode();
        }
        //mapCustomer.put(customer.getId(),customer);
        return null;
    }

    /**
     * @return : tra ve toan bo danh sach customer trong he thong
     */
    public List<Customer> findAll() {
        //TODO: Implement method  dac ta
        List<Customer> list = new ArrayList<Customer>(mapCustomer.values());
        return list;
    }

    /**
     * @param id: Id cua customer muon delete
     */
    public void deleteById(String id) throws CustomerNotFoundException {
        try{
            if (mapCustomer.containsKey(id)){
                        mapCustomer.remove(id);
                }
            }
        catch (CustomerNotFoundException e){
            e.getErrorCode();
            }
        }
        //TODO: Implement method  dac ta
    }

