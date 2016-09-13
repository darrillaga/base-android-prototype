package me.darrillaga.prototype.viewer.viewpendingorders.view;

import ioio.lib.api.IOIO;

public interface IOIOState {
    IOIO getIOIO();
    IOIOLooperState getLooperState();
}