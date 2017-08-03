package me.piebridge.brevent.ui;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.text.Collator;
import java.util.Comparator;
import java.util.Objects;

/**
 * Created by thom on 2017/1/25.
 */
class AppsInfo {

    private static final int PRIME_HASH = 59;

    private static final int PRIME_NULL = 43;

    private static final String EMPTY = "";

    private static final int STATUS_UNKNOWN = -1;

    static final int STATUS_RUNNING = 0;

    static final int STATUS_STANDBY = 1;

    static final int STATUS_STOPPED = 2;

    final String packageName;

    String label = EMPTY;

    int status = STATUS_UNKNOWN;

    boolean updated = false;

    final boolean hasName;

    long lastUpdateTime;

    AppsInfo(String packageName, String label) {
        this.packageName = packageName;
        this.label = label;
        this.hasName = label != null && !label.startsWith(packageName);
    }

    AppsInfo(Integer status, String label) {
        this.status = status;
        this.label = label;
        this.packageName = EMPTY;
        this.hasName = false;
    }

    int compareTo(@NonNull AppsInfo another) {
        if (status < another.status) {
            return -1;
        } else if (status > another.status) {
            return 1;
        } else if (TextUtils.isEmpty(packageName)) {
            return -1;
        } else if (TextUtils.isEmpty(another.packageName)) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof AppsInfo) {
            AppsInfo other = (AppsInfo) obj;
            return status == other.status
                    && Objects.equals(label, other.label)
                    && Objects.equals(packageName, other.packageName);

        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = PRIME_HASH + (packageName == null ? PRIME_NULL : packageName.hashCode());
        result = result * PRIME_HASH + (label == null ? PRIME_NULL : label.hashCode());
        result = result * PRIME_HASH + status;
        return result;
    }

    public boolean isPackage() {
        return !EMPTY.equals(packageName);
    }

    public static class SortByTime implements Comparator<AppsInfo> {

        @Override
        public int compare(AppsInfo o1, AppsInfo o2) {
            int result = o1.compareTo(o2);
            if (result == 0) {
                return Long.compare(o2.lastUpdateTime, o1.lastUpdateTime);
            } else {
                return result;
            }
        }

    }

    public static class SortByName implements Comparator<AppsInfo> {

        @Override
        public int compare(AppsInfo o1, AppsInfo o2) {
            int result = o1.compareTo(o2);
            if (result == 0) {
                result = Boolean.compare(o2.hasName, o1.hasName);
            }
            if (result == 0) {
                return Collator.getInstance().compare(o1.label, o2.label);
            } else {
                return result;
            }
        }

    }


}
