import Header from "../../components/header/header";
import MenuLayout from "../../components/menu/menu-layout";
import UserForm from "../../components/forms/user-form";
import PasswordForm from "../../components/forms/password-form";
import { menuItems } from "../../utils/nav-items";
import { useState } from "react";

const UpdateUser = () =>
{
    const [isUserForm, setIsUserForm] = useState(true);

    return(

        <>
            <Header status="logged" />
            <MenuLayout navItems={menuItems}>

                {
                    isUserForm ?
                    <UserForm setIsUserForm={setIsUserForm}/> :
                    <PasswordForm setIsUserForm={setIsUserForm}/>
                }

            </MenuLayout>
        </>

    )
}

export default UpdateUser