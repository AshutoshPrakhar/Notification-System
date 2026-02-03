import javax.management.Notification;
import java.util.*;
// Decorators and Notifications
interface INotification{
    String getContent();
}
abstract class SimpleNotification implements INotification{
    String text;
    public SimpleNotification(String text) {
        this.text = text;
    }
    @Override
    public String getContent() {
        return text;
    }
}
abstract class NotificationDecorator implements INotification{
    INotification notification;
    public NotificationDecorator(INotification iNotification) {
        this.notification = iNotification;
    }
}
class TimeStampDecorator extends NotificationDecorator {
    public TimeStampDecorator(INotification iNotification) {
        super(iNotification);
    }
    @Override
    public String getContent() {
        return TimeUtils.getCurrentTime() + notification.getContent();
    }
}
class SignatureDecorator extends NotificationDecorator{
    private String signature;
    public SignatureDecorator(INotification iNotification, String signature) {
        super(iNotification);
        this.signature = signature;
    }

    @Override
    public String getContent() {
        return notification.getContent() + "\n-- " + signature + "\n\n";    }
}

// Observer Design : Update the notification to all users

interface IObserver {
    void update();
}
interface IObservable{
    void addObserver(IObserver observer);
    void removeObserver(IObserver observer);
    void notifyObserver();
}
class NotificationObservable implements IObservable{
    private INotification currNotification;
    private List<IObserver> observers = new ArrayList<>();
    @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }
    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }
    @Override
    public void notifyObserver() {
        for(IObserver observer : observers){
            observer.update();
        }
    }

    public INotification getCurrNotification() {
        return currNotification;
    }

    public void setCurrNotification(INotification currNotification) {
        this.currNotification = currNotification;
        notifyObserver();
    }
    public String getNotificationContent(){
        return currNotification.getContent();
    }
}
class Logger implements IObserver{
    private NotificationObservable notificationObservable;

    public Logger(NotificationObservable notificationObservable) {
        this.notificationObservable = notificationObservable;
    }

    @Override
    public void update() {
        System.out.println("Logging New Notification : \n" + notificationObservable .getNotificationContent());
    }
}

// Strategy Pattern

interface INotificationStrategy{
    void sendNotification(String content);
}
class SMSNotification implements INotificationStrategy{
    public SMSNotification(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    private String mobileNumber;
    @Override
    public void sendNotification(String content) {
        System.out.println("Sending the notification through SMS: " + mobileNumber + "\n" + content );
    }
}
class EmailNotification implements INotificationStrategy{
    public EmailNotification(String emailId) {
        this.emailId = emailId;
    }

    private String emailId;
    @Override
    public void sendNotification(String content) {
        System.out.println("Sending notificaion through Email: " + emailId + "\n" + content);
    }
}
class PopUpNotification implements INotificationStrategy{

    @Override
    public void sendNotification(String content) {
        System.out.println("Sending notification through Pop Up: " + content);
    }
}
class NotificationEngine implements IObserver{
    private NotificationObservable notificationObservable;
    private List<INotificationStrategy> notificationStrategies = new ArrayList<>();
    public NotificationEngine(NotificationObservable notificationObservable) {
        this.notificationObservable = notificationObservable;
    }
    public void addNotificationStrategy(INotificationStrategy strategy){
        this.notificationStrategies.add(strategy);
    }
    @Override
    public void update() {
        String notificationContent = notificationObservable.getNotificationContent();
        for (INotificationStrategy allStrategy : notificationStrategies){
            allStrategy.sendNotification(notificationContent);
        }
    }
}


// The NotificationService manages notifications. It keeps track of notifications.
// Any client code will interact with this service.
// Singleton Class
class NotificationService{
    private List<INotification> notifications = new ArrayList<>();
    private NotificationObservable notificationObservable;
    private static NotificationService notificationServiceInstance;

    private NotificationService() {
        this.notificationObservable = new NotificationObservable();
    }

    public static NotificationService getInstance(){
        if(notificationServiceInstance == null){
            notificationServiceInstance = new NotificationService();
        }
        return notificationServiceInstance;
    }
    public NotificationObservable getObservable(){
        return notificationObservable;
    }
    public void sendNotification(INotification notification){
        notifications.add(notification);
        notificationObservable.setCurrNotification(notification);
    }
}



public class NotificationSystem {
    public static void main(String[] args) {
        // Getting Notification Service
        NotificationService notificationService = NotificationService.getInstance();

        // Getting observable
        NotificationObservable notificationObservable = notificationService.getObservable();

        // Create Logger Observer
        Logger logger = new Logger(notificationObservable);

        // Creating Notification Engine for Observers
        NotificationEngine notificationEngine = new NotificationEngine(notificationObservable);

        notificationEngine.addNotificationStrategy(new EmailNotification("email@gmail.com"));
        notificationEngine.addNotificationStrategy(new SMSNotification("9123456789"));
        notificationEngine.addNotificationStrategy(new PopUpNotification());

        // Attach to observers
        notificationObservable.addObserver(logger);
        notificationObservable.addObserver(notificationEngine);

        // Creating notification with decorators
        INotification notification = new SimpleNotification("Your order has been shipped!") {};
        notification = new TimeStampDecorator(notification);
        notification = new SignatureDecorator(notification, "Customer Care");

        notificationService.sendNotification(notification);

    }
}