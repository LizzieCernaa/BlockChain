public class ServidorModel {

    private String name;

    private String ip;

    private int port;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o){
        ServidorModel server = (ServidorModel) o;
        return server.name.equals(this.name);
    }


}
