package co.bugg.advancedxp.commands;

import co.bugg.advancedxp.gui.MainGui;
import co.bugg.advancedxp.util.TickDelay;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class BaseCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "axp";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/axp <args...>";
    }

    @Override
    public List<String> getCommandAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("advancedxp");
        return aliases;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        new TickDelay(() -> Minecraft.getMinecraft().displayGuiScreen(new MainGui()), 2);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos targetPos) {
        return new ArrayList<>();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
