package org.example.notificationserice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
its good practice to not share classes between services because we want to keep
the services clean and independent

especially if you are trying to receive and send payloads, have your own version
of these classes inside your own services
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {
    private String orderNumber;
}
