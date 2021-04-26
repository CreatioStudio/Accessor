package vip.creatio.accessor;

public enum DelegationType {

    /**
     * Delegate a method
     */
    METHOD,

    /**
     * Delegate a field
     */
    FIELD,

    /**
     * Delegate a class, can only be used on class.
     * When a class was marked with Delegation(DelegationType.CLASS),
     * the other Delegation tag under it will inherit it's class.
     */
    CLASS;

}
