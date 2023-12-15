import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.amitghosh.foodiepal.adapter.MealPlannerAdapter
import me.amitghosh.foodiepal.model.Blog
import me.amitghosh.foodiepal.model.Meal


class BlogAdapter(private val blogs: List<Blog>, val context: Context) : RecyclerView.Adapter<BlogAdapter.ViewHolder>() {
    // Other necessary adapter code
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Other binding logic

        holder.title.text = blogs[position].title
        holder.content.text = blogs[position].content
        holder.date.text = "Published on ${blogs[position].date}"

        // Set click listener for the share button
        holder.btnShare.setOnClickListener{
            shareBlogPost(blogs[position])
        }
    }

    // Other adapter methods
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Other view holder fields
        val btnShare: ImageButton
        val content: TextView
        val title: TextView
        val date: TextView


        init {

            // Other view holder initialization
            btnShare = itemView.findViewById(me.amitghosh.foodiepal.R.id.btnShare)
            title = itemView.findViewById(me.amitghosh.foodiepal.R.id.textViewTitle)
            content = itemView.findViewById(me.amitghosh.foodiepal.R.id.textViewContent)
            date = itemView.findViewById(me.amitghosh.foodiepal.R.id.textViewDate)
        }
    }

    // Other adapter methods
    // Share logic
    private fun shareBlogPost(blogPost: Blog) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, blogPost.title)
        shareIntent.putExtra(Intent.EXTRA_TEXT, blogPost.content)

        // Start the chooser activity
        context.startActivity(Intent.createChooser(shareIntent, "Share Blog Post"))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(me.amitghosh.foodiepal.R.layout.blog_item, parent, false);
        return BlogAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  blogs.size;
    }
}