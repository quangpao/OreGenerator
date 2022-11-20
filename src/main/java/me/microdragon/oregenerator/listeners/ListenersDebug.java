package me.microdragon.oregenerator.listeners;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class ListenersDebug extends Listeners implements Listener {

    public ListenersDebug() {
        super();
    }

    @EventHandler
    public void onFromTo(BlockFromToEvent event) {
        System.out.println("--------- BlockFromToEvent: onFromTo() ---------");
        System.out.println("Block: " + event.getBlock().getType());
        System.out.println("ToBlock: " + event.getToBlock().getType());
        System.out.println("Face: " + event.getFace());
        System.out.println("Cancelled: " + event.isCancelled());
        System.out.println("Event calling...");
        super.onFromTo(event);
        System.out.println("Cancelled: " + event.isCancelled());
        System.out.println("--------- BlockFromToEvent: onFromTo() ---------");
        System.out.println();
        System.out.println("--------- generateCobble() ---------");
        System.out.println("Material: " + event.getBlock().getType());
        System.out.println("Block: " + event.getToBlock());
//        System.out.println("Result: " + super.generateCobble(event.getToBlock()));
        System.out.println("--------- generateCobble() ---------");
        System.out.println();
        System.out.println();
    }

}
