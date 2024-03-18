package ru.fsv67.services.itinerarySheet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.fsv67.models.itinerarySheet.Address;
import ru.fsv67.models.itinerarySheet.AddressView;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AddressService {

    public List<Address> mapToAddressList(List<AddressView> addressViewList) {
        List<Address> addressList = new ArrayList<>();
        for (AddressView addressView : addressViewList) {
            addressList.add(new Address(
                    addressView.getDepartureAddress().getValue(),
                    addressView.getDestinationAddress().getValue()
            ));
        }
        return addressList;
    }
//
//    public List<AddressView> setNumberLine(List<AddressView> addressViewList) {
//        long numberLine = 1L;
//        for (AddressView addressView : addressViewList) {
//            addressView.setNumberLine(new SimpleLongProperty(numberLine++));
//        }
//        return addressViewList;
//    }

    public List<AddressView> mapToAddressViewList(List<Address> addressList) {
        List<AddressView> addressViewList = new ArrayList<>();
        long numberLine = 1L;
        for (Address address : addressList) {
            addressViewList.add(
                    new AddressView(
                            numberLine++,
                            address.getDepartureAddress(),
                            address.getDestinationAddress()
                    )
            );
        }
        return addressViewList;
    }
}
