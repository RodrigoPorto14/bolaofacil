import { useLocation } from "react-router-dom";
import Button from "./button"
import { previousPath } from "../../utils/path-handler";

const BackButton = () =>
{
    const location = useLocation();
    const back = previousPath(location.pathname) 

    return(

        <Button color="bg-gray-400" hoverColor="hover:bg-gray-600" type="button" to={back} > VOLTAR </Button>
    )
}

export default BackButton;