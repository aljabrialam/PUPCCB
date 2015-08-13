package pupccb.solutionsresource.com.helper.communicator;


import pupccb.solutionsresource.com.fragment.CardGrid;

/**
 */
public class InterfaceMapping {
    private static InterfaceMapping instance;
    private CardGrid fragment;

    private InterfaceMapping() {

    }

    public static InterfaceMapping getInstance() {
        if (instance == null)
            instance = new InterfaceMapping();
        return instance;
    }

    public void setFragment(CardGrid fragment) {
        this.fragment = fragment;
    }

    public CardGrid getFragment() {
        return fragment;
    }
}
