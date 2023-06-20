import Header from '../components/header'
import Button from '../components/button'

const Home = () =>
{
    return(

        <>

            <Header  status='not-logged' />

            <main className="h-full w-ful flex flex-col-reverse gap-4 lg:flex-row lg:justify-evenly items-center mt-12">

                <div className='flex flex-col h-[500px] w-[450px] gap-4 items-center'>
                    <p className="text-xl">
                        Lorem ipsum dolor sit amet consectetur adipisicing elit. Ex iusto aliquid accusamus recusandae voluptates quasi ea eveniet quam est qui, veritatis repellendus! Vitae dicta consequatur distinctio! Neque libero dolor sapiente! Lorem ipsum dolor sit amet consectetur adipisicing elit. Ex iusto aliquid accusamus recusandae voluptates quasi ea eveniet quam est qui, veritatis repellendus! Vitae dicta consequatur distinctio! Neque libero dolor sapiente! Lorem ipsum dolor sit amet consectetur adipisicing elit. Ex iusto aliquid accusamus recusandae voluptates quasi ea eveniet quam est qui, veritatis repellendus! Vitae dicta consequatur distinctio! Neque libero dolor sapiente!
                    </p>

                    <Button to="/login" >CRIAR BOLÃO</Button>
                </div>

                <div className='bg-blue-300 h-[500px] w-[450px]'>
                    IMAGEM
                </div>

            </main>

        </>
            
    )
}

export default Home