import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable
{
        private int id;
        private long timeStamp;
        private String sender;
        private String receiver;
        private double amount;

        public Transaction(int pId, String pSender,
                           String pReceiver, double pAmount)
        {
            this.id=pId;
            this.timeStamp= new Date().getTime();
            this.sender=pSender;
            this.receiver=pReceiver;
            this.amount=pAmount;
        }

        @Override
        public String toString() {
            return Integer.toString(id)+
                    Long.toString(timeStamp)+
                    sender+receiver+
                    Double.toString(amount);
        }

        /**
         * @return the id
         */
        public int getId() {
            return id;
        }

        /**
         * @return the timeStamp
         */
        public long getTimeStamp() {
            return timeStamp;
        }

        /**
         * @return the sender
         */
        public String getSender() {
            return sender;
        }

        /**
         * @return the receiver
         */
        public String getReceiver() {
            return receiver;
        }

        /**
         * @return the amount
         */
        public double getAmount() {
            return amount;
        }
}
