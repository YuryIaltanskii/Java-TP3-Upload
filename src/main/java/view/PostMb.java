package view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import auth.AuthUserMb;
import controller.PostController;
import model.Post;

@Named
public class PostMb {
	
	@Inject
	private AuthUserMb authMb;
	
	@Inject 
	private PostController postCntl;
	
    @NotNull
    @Size(min=2,max=255)
	private String content;
	
	public void submitPost(){
		try{
			postCntl.addPost(authMb.getUser(), content);
			content = null;
		} catch (Exception e){
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al enviar el post", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public List<Post> getPostList(){
		try{
			return postCntl.all(0, 10);
		} catch (Exception e){
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error del metodo getPostList", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}
	
	public List<Post> getMyPostList(){
		try{
			return postCntl.from(authMb.getUser(), 0, 10);
		} catch (Exception e){
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error del metodo getMyPostList", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
