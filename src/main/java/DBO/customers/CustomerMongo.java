package DBO.customers;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.bson.types.ObjectId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class CustomerMongo implements Serializable {
    @JsonProperty("_id")
    private Id id;
    private String email;
    private String name;
    private int active;

    public CustomerMongo(Id id, String email, String name, int active) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.active = active;
    }

    public CustomerMongo(String email, String name, int active) {
        this.id = new Id();
        this.email = email;
        this.name = name;
        this.active = active;
    }

    public CustomerMongo() {
        this.id = new Id();
        this.email = RandomStringUtils.randomAlphanumeric(5) + "@test.tt";
        this.name = RandomStringUtils.randomAlphabetic(5);
        this.active = RandomUtils.nextInt(0, 1);
    }

    public class Id {
        @JsonProperty("$oid")
        private String oid;

        public Id(String oid) {
            this.oid = oid;
        }

        public Id() {
            this.oid = new ObjectId().toString();
        }

        public String getOid() {
            return oid;
        }

        @Override
        public String toString() {
            return "Id{" +
                    "oid='" + oid + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id id = (Id) o;
            return Objects.equals(oid, id.oid);
        }

        @Override
        public int hashCode() {
            return Objects.hash(oid);
        }
    }

    public Id getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerMongo that = (CustomerMongo) o;
        return active == that.active && Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, active);
    }
}
