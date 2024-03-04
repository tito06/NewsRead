package com.example.newsread

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.newsread.ui.theme.NewsReadTheme
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.http.toHttpDateOrNull


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: NewsVm by viewModels()
    private val splashViewModel: SplashVm by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition{splashViewModel.isLoading.value}

        setContent {
            NewsReadTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NewsUi(news = viewModel.newsList, newsVm = viewModel)
                    viewModel.fetchNews()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar() {

    val fonts = FontFamily(
        Font(R.font.poppins_medium, weight = FontWeight.Normal)
    )

    TopAppBar(title = {
        Text(text = "NewsRead",
            fontFamily = fonts,
            color = colorResource(id = R.color.darkGreen),
            modifier = Modifier.padding(0.dp,8.dp,8.dp,8.dp))
    },
    modifier = Modifier
        .fillMaxWidth()
        .height(50.dp),
        actions = {
            IconButton(onClick = {/* Do Something*/ }) {
                Icon(Icons.Filled.Notifications, null)
            }
            IconButton(onClick = {/* Do Something*/ }) {
                Icon(Icons.Filled.Search, null)
            }
        })
}

@Composable
fun NewsUi(news:List<Article>, newsVm:NewsVm){

    val fontHeading = FontFamily(
        Font(R.font.poppins_medium, weight = FontWeight.Normal)
    )




    Column {


        CustomAppBar()
        Text(text = "LATEST NEWS",
            fontFamily = fontHeading,
            modifier = Modifier.padding(10.dp))
        Spacer(modifier = Modifier.height(4.dp))
        NewsBanner(news =  newsVm.newsList)

        Text(text = "ALL NEWS",
            fontFamily = fontHeading,
            modifier = Modifier.padding(10.dp))
        Spacer(modifier = Modifier.height(4.dp))
        if (news.isEmpty()){
            CircularProgressIndicator()
        }else {
            LazyColumn{
                itemsIndexed(items = news){index, item ->
                    ArticleData(item)
                }
            }
        }
    }
}


@Composable
fun NewsBanner(news: List<Article>){


    LazyRow{
        itemsIndexed(items = news){index, item ->
            BannerData(article = item)
        }
    }
}


@Composable
fun BannerData(article: Article){


    val fontSubHeader = FontFamily(
        Font(R.font.poppins_light, weight = FontWeight.Normal)
    )


            Box(modifier = Modifier
                .width(250.dp)
                .height(140.dp)
                .padding(8.dp, 4.dp)
                .background(
                    color = Color.Transparent,
                    RoundedCornerShape(8.dp)
                ),

            ) {

                Column(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(4.dp, 4.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Image(

                        painter = rememberAsyncImagePainter(article.urlToImage),
                        contentDescription = "content Image",

                        modifier = Modifier
                            .height(70.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillWidth
                    )
                    Text(
                        text = article.title,
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontFamily = fontSubHeader,
                    )
                }



        }

}


@Composable
fun ArticleData(article: Article){

    val fontSubHeader = FontFamily(
        Font(R.font.poppins_light, weight = FontWeight.Normal)
    )

    val fontAuthor = FontFamily(
        Font(R.font.poppins_thin, weight = FontWeight.Normal)
    )

    Card(modifier = Modifier
        .fillMaxWidth()
        .height(110.dp)
        .padding(8.dp, 4.dp)
        .background(color = Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {

        Row(modifier = Modifier
            .padding(4.dp)
            .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween) {


            Card(modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .background(color = Color.Transparent),
                shape = RoundedCornerShape(2.dp)
            ) {
                Image(

                    painter = rememberAsyncImagePainter(article.urlToImage),
                    contentDescription = "content Image",

                    modifier = Modifier
                        .wrapContentSize()
                        .fillMaxHeight()
                        .wrapContentWidth(),
                    contentScale = ContentScale.FillHeight
                )
            }

            
            Box(modifier = Modifier.width(3.dp))


            Column ( verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(10.dp, 4.dp, 4.dp, 4.dp)
                    .fillMaxHeight()
                    .weight(0.8f)){

                if (article.author == null){
                    Text(
                        text = "No NAME",
                        fontFamily = fontAuthor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp

                    )
                } else {
                    Text(
                        text = article.author.toString(),
                        fontFamily = fontAuthor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                }

                Text(
                    text = article.title,
                    fontFamily = fontSubHeader,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp

                )



            }

        }

    }

}
