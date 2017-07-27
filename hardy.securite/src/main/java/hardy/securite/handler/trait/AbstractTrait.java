package hardy.securite.handler.trait;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import hardy.securite.TraitRefuse;
import hardy.securite.exception.RefuseRequestException;
/**
 * 实现基本的TraitRefuse方法
 * @author ssk
 *
 */
public class AbstractTrait implements TraitRefuse{

	@Override
	public void taiterRefuseRequestException(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			RefuseRequestException e) {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
            try{
             out = response.getWriter();
             out.println("<html>") ;
             out.println("<head>");
             out.println("<title>identider invalide</title>") ;
             out.println("</head>") ;
             out.println("<body>") ;
             out.print("hardy.securite<br>");
             out.print("cause:"+e.getMessage()+"<br>");
             out.print("cette page indique voutr<br>");
             out.print("Cette description de page de votre permission a été bloqué.<br>");
             out.print("Si l'effet voulu changer cette pointe de page peut être modifiée<br>");
             out.print("TraitRefuse ou AbstractTraitRefuse<br>");
             out.println("</body>") ;
             out.println("</html>") ;
            } catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
            finally { 
	            out.close();
	        }
		
	}

	@Override
	public void taiterRefuseRequest(HttpServletRequest request,
			HttpServletResponse response, Object handler) {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
            try{
             out = response.getWriter();
             out.println("<html>") ;
             out.println("<head>");
             out.println("<title>identider invalide</title>") ;
             out.println("</head>") ;
             out.println("<body>") ;
             out.print("hardy.securite<br>");
             out.print("cette page indique voutr<br>");
             out.print("Cette description de page de votre permission a été bloqué.<br>");
             out.print("Si l'effet voulu changer cette pointe de page peut être modifiée<br>");
             out.print("TraitRefuse ou AbstractTraitRefuse<br>");
             out.println("</body>") ;
             out.println("</html>") ;
            } catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
            finally { 
	            out.close();
	        }
	}

	@Override
	public void taiterCompletRequest(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception e) {
		PrintWriter out = null;
        try{
         out = response.getWriter();
         out.println("<html>") ;
         out.println("<head>");
         out.println("<title>identider invalide</title>") ;
         out.println("</head>") ;
         out.println("<body>") ;
         out.print("hardy.securite<br>");
         out.print("cause:"+e.getMessage()+"<br>");
         out.print("cette page indique voutr<br>");
         out.print("Cette description de page de votre request throw Exception.<br>");
         out.print("Si l'effet voulu changer cette pointe de page peut être modifiée<br>");
         out.print("TraitRefuse ou AbstractTraitRefuse<br>");
         out.println("</body>") ;
         out.println("</html>") ;
        } catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
        finally { 
            out.close();
        }
	}

	@Override
	public void afterPost(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) {
//		 System.out.println("programe call afterPost for "
//			+request.getRequestURI()+" modelAndView["+ 
//			modelAndView.toString()+"]");
	}

	 

}
