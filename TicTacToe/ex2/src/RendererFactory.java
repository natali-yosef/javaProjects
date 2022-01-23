/**
 * factory of renderer
 */
public class RendererFactory {

    /**
     *
     * @param strRenderer- the name of the renderer
     * @return - the renderer object
     */
    public Renderer buildRenderer(String strRenderer){

        switch (strRenderer) {
            case "console":
                return new ConsoleRenderer();
            case "none":
                return new VoidRenderer();
            default:
                return null;
        }
    }
}
