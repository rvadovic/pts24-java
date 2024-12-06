package sk.uniba.fmph.dcs.game_phase_controller;

import sk.uniba.fmph.dcs.game_board.CurrentThrow;
import sk.uniba.fmph.dcs.game_board.GameBoard;
import sk.uniba.fmph.dcs.game_board.RewardMenu;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class GamePhaseControllerFactory {
    public GamePhaseControllerFactory(){ }

    public static GamePhaseController createGamePhaseController(Map<Location,InterfaceFigureLocation> locationFigureLocation,
                                                                Map<PlayerOrder, InterfaceFeedTribe> feedTribePlayerBoards,
                                                                Map<PlayerOrder, InterfaceNewTurn> newTurnPlayerBoards,
                                                                PlayerOrder startingPlayer, InterfaceToolUse currentThrow,
                                                                InterfaceTakeReward rewardMenu){
        ArrayList<InterfaceFigureLocation> locationsList = initializeLocationsList(locationFigureLocation);

        Map<GamePhase, InterfaceGamePhaseState> gamePhaseMap = new HashMap<>();
        gamePhaseMap.put(GamePhase.PLACE_FIGURES, new PlaceFigureState(locationFigureLocation));
        gamePhaseMap.put(GamePhase.MAKE_ACTION, new MakeActionState(locationFigureLocation));
        gamePhaseMap.put(GamePhase.WAITING_FOR_TOOL_USE, new WaitingForToolUseState(initializeToolUseMap(currentThrow, feedTribePlayerBoards)));
        gamePhaseMap.put(GamePhase.ALL_PLAYERS_TAKE_A_REWARD, new AllPlayersTakeARewardState(rewardMenu));
        gamePhaseMap.put(GamePhase.FEED_TRIBE, new FeedTribeState(feedTribePlayerBoards));
        gamePhaseMap.put(GamePhase.NEW_ROUND, new NewRoundState(locationsList,newTurnPlayerBoards));
        gamePhaseMap.put(GamePhase.GAME_END, new GameEndState());

        return new GamePhaseController(gamePhaseMap, startingPlayer);
    }

    private static Map<PlayerOrder, InterfaceToolUse> initializeToolUseMap(InterfaceToolUse currentThrow, Map<PlayerOrder, InterfaceFeedTribe> feedTribeMap){
        Map<PlayerOrder, InterfaceToolUse> map = new HashMap<>();
        for(PlayerOrder p: feedTribeMap.keySet()){
            map.put(p, currentThrow);
        }
        return map;
    }

    private static ArrayList<InterfaceFigureLocation> initializeLocationsList(Map<Location, InterfaceFigureLocation> locationsMap){
        ArrayList<InterfaceFigureLocation> locationsList = new ArrayList<>();
        if(locationsMap.containsKey(Location.CIVILISATION_CARD4)){
            locationsList.add(locationsMap.get(Location.CIVILISATION_CARD4));
        }
        if(locationsMap.containsKey(Location.CIVILISATION_CARD3)){
            locationsList.add(locationsMap.get(Location.CIVILISATION_CARD3));
        }
        if(locationsMap.containsKey(Location.CIVILISATION_CARD2)){
            locationsList.add(locationsMap.get(Location.CIVILISATION_CARD2));
        }
        if(locationsMap.containsKey(Location.CIVILISATION_CARD1)){
            locationsList.add(locationsMap.get(Location.CIVILISATION_CARD1));
        }
        for(Location l: locationsMap.keySet()){
            if(l.equals(Location.CIVILISATION_CARD4) || l.equals(Location.CIVILISATION_CARD3) || l.equals(Location.CIVILISATION_CARD2) || l.equals(Location.CIVILISATION_CARD1)){
                continue;
            }
            locationsList.add(locationsMap.get(l));
        }
        return locationsList;
    }
}
