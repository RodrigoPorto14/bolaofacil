import BackButton from "../buttons/button-back";
import DeleteButton from "../buttons/button-delete";
import MainButton from "../buttons/button-main";
import { FormProps } from "../../utils/types";

const FormLayout = ({children, onSubmit, buttonName, onDelete, resource, create, backButton=true} : FormProps ) =>
{
    return(
        <>
        {
            (create || resource) &&
            (
                <form noValidate onSubmit={onSubmit} className="flex flex-col h-full w-full justify-between">

                    <div className={`flex flex-col gap-2 items-start`}>

                        {children}
                                    
                    </div>
                                
                    <div className="flex gap-4 flex-wrap justify-center">
                        { backButton && <BackButton /> }
                        <MainButton> {buttonName} </MainButton>
                        { onDelete && <DeleteButton onClick={onDelete} /> }
                    </div>

                </form>
            )    
        }
        </>  
    )
}

export default FormLayout;