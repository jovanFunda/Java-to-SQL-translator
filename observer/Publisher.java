package observer;

public interface Publisher {
    void addSubscriber(Subscriber sub);
    void removeSubscriber(Subscriber sub);
    void notifySubscribers(Notification notification);
    void notifySubscribers(Object object);
}
