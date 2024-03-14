package ru.fsv67.services.itinerarySheet;

import javafx.beans.property.SimpleLongProperty;
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
        int numberLine = 1;
        List<Address> addressList = new ArrayList<>();
        for (AddressView addressView : addressViewList) {
            if (numberLine == 1) {
                addressList.add(mapToAddress(addressView.getDepartureAddress().getValue()));
            }
            addressList.add(mapToAddress(addressView.getDestinationAddress().getValue()));
            numberLine++;
        }
        return addressList;
    }

    private Address mapToAddress(String addressView) {
        return new Address(addressView);
    }

    public List<AddressView> setNumberLine(List<AddressView> addressViewList) {
        long numberLine = 1L;
        for (AddressView addressView : addressViewList) {
            addressView.setNumberLine(new SimpleLongProperty(numberLine++));
        }
        return addressViewList;
    }

    public List<AddressView> mapToAddressViewList(List<Address> addressList) {
        List<AddressView> addressViewList = new ArrayList<>();
        for (int i = 1; i < addressList.size(); i++) {
            addressViewList.add(
                    new AddressView(
                            addressList.get(i).getId(),
                            Integer.toUnsignedLong(i),
                            addressList.get(i - 1).getAddress(),
                            addressList.get(i).getAddress()
                    )
            );
        }
        return addressViewList;
    }
}
