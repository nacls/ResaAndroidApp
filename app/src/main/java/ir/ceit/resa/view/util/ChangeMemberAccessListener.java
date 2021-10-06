package ir.ceit.resa.view.util;

public interface ChangeMemberAccessListener {

    void removeMemberFromBoardClicked(String username);

    void giveMemberWriterAccessClicked(String username);

    void takeWriterAccessFromMemberClicked(String username);
}
