package me.darrillaga.prototype.viewer.childrenguardian.view;

import android.os.Bundle;
import android.os.Parcelable;

import com.hannesdorfmann.fragmentargs.bundler.ArgsBundler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This {@link ArgsBundler} takes a java.util.List and casts it to an ArrayList. So it assumes that
 * the List is instance of ArrayList.
 * <p>
 * With this ArgsBundler you can annotate fields of type java.util.List like that
 * <code>@Arg(bundler
 * = CastedSerializableArrayListArgsBundler.class) List<Foo> foos></code>
 * </p>
 */
public class CastedSerializableArrayListArgsBundler<T extends Serializable> implements ArgsBundler<List<T>> {

  @Override public void put(String key, List<T> value, Bundle bundle) {
    if (!(value instanceof ArrayList)) {
      throw new ClassCastException(
          "CastedArrayListArgsBundler assumes that the List is instance of ArrayList, but it's instance of "
              + value.getClass().getCanonicalName());
    }

    bundle.putSerializable(key, (ArrayList<T>) value);
  }

  @Override public List<T> get(String key, Bundle bundle) {
    return (List<T>) bundle.getSerializable(key);
  }
}
