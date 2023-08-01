import Header from '../components/header/header'
import MainButton from '../components/buttons/button-main'

const Home = () =>
{
    return(

        <>

            <Header  status='not-logged' />

            <main className="flex flex-grow items-center p-8 gap-4 flex-col-reverse lg:flex-row lg:justify-evenly ">


                <div className='flex flex-col max-w-[500px] gap-4 items-center'>
                    <p className="text-xl">
                        Lorem ipsum dolor sit amet consectetur adipisicing elit. Ex iusto aliquid accusamus recusandae voluptates quasi ea eveniet quam est qui, veritatis repellendus! Vitae dicta consequatur distinctio! Neque libero dolor sapiente! Lorem ipsum dolor sit amet consectetur adipisicing elit. Ex iusto aliquid accusamus recusandae voluptates quasi ea eveniet quam est qui, veritatis repellendus! Vitae dicta consequatur distinctio! Neque libero dolor sapiente! Lorem ipsum dolor sit amet consectetur adipisicing elit. Ex iusto aliquid accusamus recusandae voluptates quasi ea eveniet quam est qui, veritatis repellendus! Vitae dicta consequatur distinctio! Neque libero dolor sapiente!
                    </p>

                    <MainButton to="/login" >CRIAR BOLÃO </MainButton>
                </div>

                <div className='bg-blue-300 w-[350px] h-[350px] sm:w-[500px] sm:h-[500px]'>
                    IMAGEM
                </div>

            </main>

        </>
            
    )
}

export default Home