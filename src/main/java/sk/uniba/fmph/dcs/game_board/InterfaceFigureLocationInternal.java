package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;

public interface InterfaceFigureLocationInternal {
    boolean placeFigures(Player player, int figureCount);

    HasAction tryToPlaceFigures(Player payer, int count);

    ActionResult makeAction(Player player, Effect[] inputResources, Effect[] outputResources);

    boolean skipAction(Player player);

    HasAction tryToMakeAction(Player player);

    boolean newTurn();
}
