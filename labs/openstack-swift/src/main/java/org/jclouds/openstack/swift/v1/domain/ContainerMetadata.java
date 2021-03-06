package org.jclouds.openstack.swift.v1.domain;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

/**
 * retrieve a list of existing storage containers ordered by name. The sort order for the name is
 * based on a binary comparison, a single built-in collating sequence that compares string data
 * using SQLite's memcmp() function, regardless of text encoding.
 * 
 * @author Adrian Cole
 * @see <a
 *      href="http://docs.openstack.org/api/openstack-object-storage/1.0/content/s_listcontainers.html">api
 *      doc</a>
 */
public class ContainerMetadata implements Comparable<ContainerMetadata> {

   public static Builder builder() {
      return new Builder();
   }

   public Builder toBuilder() {
      return builder().fromAccountMetadata(this);
   }

   public static class Builder {
      protected String name;
      protected int count;
      protected int bytes;

      /**
       * @see ContainerMetadata#getName()
       */
      public Builder name(String name) {
         this.name = checkNotNull(name, "name");
         return this;
      }

      /**
       * @see ContainerMetadata#getCount()
       */
      public Builder count(int count) {
         this.count = count;
         return this;
      }

      /**
       * @see ContainerMetadata#getBytes()
       */
      public Builder bytes(int bytes) {
         this.bytes = bytes;
         return this;
      }

      public ContainerMetadata build() {
         return new ContainerMetadata(name, count, bytes);
      }

      public Builder fromAccountMetadata(ContainerMetadata from) {
         return name(from.getName()).count(from.getCount()).bytes(from.getBytes());
      }
   }

   protected ContainerMetadata() {
      // we want serializers like Gson to work w/o using sun.misc.Unsafe,
      // prohibited in GAE. This also implies fields are not final.
      // see http://code.google.com/p/jclouds/issues/detail?id=925
   }
  
   protected String name;
   protected int count;
   protected int bytes;

   public ContainerMetadata(String name, int count, int bytes) {
      this.name = checkNotNull(name, "name");
      this.count = count;
      this.bytes = bytes;
   }

   /**
    * 
    * @return the name of the container
    */
   public String getName() {
      return name;
   }

   /**
    * 
    * @return the number of objects in the container
    */
   public int getCount() {
      return count;
   }

   /**
    * @return the total bytes stored in this container
    */
   public int getBytes() {
      return bytes;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
      if (object instanceof ContainerMetadata) {
         final ContainerMetadata other = ContainerMetadata.class.cast(object);
         return equal(getName(), other.getName()) && equal(getCount(), other.getCount())
                  && equal(getBytes(), other.getBytes());
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(getName(), getCount(), getBytes());
   }

   @Override
   public String toString() {
      return string().toString();
   }

   protected ToStringHelper string() {
      return toStringHelper("").add("name", getName()).add("count", getCount()).add("bytes", getBytes());
   }

   @Override
   public int compareTo(ContainerMetadata that) {
      if (that == null)
         return 1;
      if (this == that)
         return 0;
      return this.getName().compareTo(that.getName());
   }

}
