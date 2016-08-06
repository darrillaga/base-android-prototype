package me.darrillaga.prototype.viewer.childrenguardian.view;

import android.os.Bundle;

import com.hannesdorfmann.fragmentargs.bundler.ArgsBundler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.darrillaga.prototype.viewer.childrenguardian.viewmodel.ChildViewModel;

/**
 * <p>
 * With this ArgsBundler you can annotate fields of type Serializable like that
 * <code>@Arg(bundler
 * = ChildViewModelSerializableArgsBundler.class) Foo foo></code>
 * </p>
 */
public class ChildViewModelSerializableArgsBundler implements ArgsBundler<ChildViewModel> {

  @Override public void put(String key, ChildViewModel value, Bundle bundle) {
    bundle.putSerializable(key, value);
  }

  @Override public ChildViewModel get(String key, Bundle bundle) {
    return (ChildViewModel) bundle.getSerializable(key);
  }
}
