# All About Reactions with the Stream Chat Android SDK



## Introduction

In modern chat apps, you need something to make the messaging platforms a bit lively and increase user engagement. Reactions have emerged as a top feature to help in this.

Stream provides a nice SDK for you to add reactions in a matter of minutes in your application. 

In this tutorial you're going to learn how to:

- Add reactions to a message
- Deleting a reaction to a message
- Cumulative Reactions
- Paginating Reactions

**Note:** This tutorial assumes you already know the basic knowledge on the Stream API's. To quickly get started checkout the [Android Chat Messaging Tutorial](https://getstream.io/tutorials/android-chat/#kotlin)

## Adding a Reaction

![reactions](/Users/harun/AndroidStudioProjects/StreamReactions/images/reactions.png)

Reactions are mostly found on most social media apps and there's a number of them which are famously known:

- Like
- Favourite/Love
- Sad/Angry
- Clap

They're mostly used to represent common quick reactions that users can have after seeing a post.  Using the Stream SDK, you can easily add reactions to your messages in a very easy way.

You'll be seeing how to add a reaction. First, you have to create a <code>Reaction</code> object which has properties of your reaction.

```Kotlin
val reaction = Reaction(
    messageId = sentMessage.id,
    type = reactionModel.name,
    score = reactionModel.score,
    extraData = mutableMapOf("customField" to 1),
)
```

In the code above:

- You're adding the <code>messageId</code> for the message that you want to react to.
- You specify the type of the reaction.
- Here, you're also adding the score for the reaction
- Lastly, you add any additional extra information.

Now, you have your reaction object ready, next you'll be seeing how to send that reaction over to the Stream Client.

To send a message reaction, you need a <code>ChannelClient</code>. A <code>ChannelClient</code> enables you to:

- Create Channels
- Add users to channels
- Add messages to channels
- React to messages in out client.

On the sample app, the client is declared at the top of the file as:

```Kotlin
private lateinit var channelClient: ChannelClient
```

It's initialised in your <code>onCreate</code> method as follows:

```kotlin
channelClient = client.channel(channelType = "messaging", channelId = "general")
```

It needs a <code>ChatClient</code> which is responsible for initializing the Stream Chat Client in your application. Now that you have everything set, to send your reaction object, you need the following code:

```Kotlin
channelClient.sendReaction(reaction).enqueue { result ->
    if (result.isSuccess) {
        val sentReaction = result.data()
        hideReactions(sentReaction)
    } else {
        showSnackBar("Adding like reaction Failed")
    }
}
```

Here, you send your reaction object to Stream and wait for the callback response which returns a success or a failure. Depending on the result, you handle the states according to your app's needs. In this scenarion you can see it hides the reaction view and shows the reaction you've tapped with the score along side it as in the image below:

<img src="/Users/harun/AndroidStudioProjects/StreamReactions/images/reactions_count.png" alt="reactions_count" style="zoom:40%;" />

Congratulations! You've just learned how to add your first message reaction. With the Stream SDK it's very easy and seemless way to do that. Next, you'll be seeing how to remove the reaction.

## Removing a Reaction

To remove a reaction, you only need the <code>messageId</code> and <code>reactionType</code>. The code to remove is as follows:

```Kotlin
channelClient.deleteReaction(
    messageId = reaction.messageId,
    reactionType = reaction.type,
).enqueue { result ->
    if (result.isSuccess) {
        hideReactionCount()
    } else {
        showSnackBar("Delete Failed")
    }
}
```

This method is similar to the one for adding reactions only difference is that you're calling the <code>deleteReaction</code> method to delete the reaction. You're also passing the <code>messageId</code> and <code>reactionType</code> for the reaction that you want to remove. You're also handling the success and error states.

## Cumulative Reactions

Another famous reaction is the clap reaction or enabling a user to react more than once. This is mostly useful in blogs and articles. The Reactions API allows this functionality out of the box. This is supported by adding the <code>score</code> in your reaction model as shown below:

```Kotlin
ReactionModel("clap",R.drawable.ic_clapping, 25)
```

This is a custom data class for showing the available reactions in our application. It has the three properties as follow:

```Kotlin
data class ReactionModel (
    val name: String,
    val image: Int,
    val score: Int
    )
```

And to add your reaction score to Stream SDK here's is what you do:

```Kotlin
val reaction = Reaction(
    messageId = sentMessage.id,
    type = reactionModel.name,
    score = reactionModel.score,
    extraData = mutableMapOf("customField" to 1),
)
```

Here you specify the <code>score</code> for your reaction. And now you can send the reaction in a similar manner as follows:

```kotlin
channelClient.sendReaction(reaction).enqueue { result->
    if (result.isSuccess) {
        val sentReaction = result.data()
        hideReactions(sentReaction)
    } else {
        showSnackBar("Adding clap reaction Failed")
    }
}
```

The code is similar to the one for adding a like reaction that you saw above.

## Paginating Reactions

On Social apps, a message or a post can get more that 10 reactions and even at times thousands of reactions. In such cases you can not display all these reactions in your app. Reactions API allows you to paginate reactions as you fetch them. In this way, you specify the number that you want to fetch according to the UI of your app.

Here's how an example of to fetch reactions with pagination:

```kotlin
channelClient.getReactions(
    messageId = messageId,
    offset = 0,
    limit = 10,
).enqueue { result ->
    if (result.isSuccess) {
        val reactions: List<Reaction> = result.data()
        binding.rvReactions.visibility = View.VISIBLE
        reactionsAdapter.submitList(reactions)
        binding.rvReactions.adapter = reactionsAdapter
    } else {
        showSnackBar("Getting Reactions Failed: ${result.error().message}")
    }
}
```

The <code>getReactions()</code> method takes in three parameters:

- <code>messageId</code> - ID of the message whose reactions you want to fetch.
- <code>offset</code>- The position at which you want to start fetching your reactions. Tis is useful when at times you want to fetch from let's say the 10th reaction. You set the offset to be 10. For this case it's 0 since you want to fetch from the first.
- <code>limit</code>- This specifies the number of reactions you want to fetch at a single time. The number is as per your needs.

From the sample project, here's how the reactions are:

![paginated_reactions](/Users/harun/AndroidStudioProjects/StreamReactions/images/paginated_reactions.png)



The API offers alot of flebility according to your needs. In the next section you'll be looking and the reactions from the API itself and how you can customize them.

## Looking at Stream Reactions

The Reactions API has the UI components for reactions already built for you incase you don't need custom ones as you've been learning in the sections above. This is how they look:

<img src="/Users/harun/AndroidStudioProjects/StreamReactions/images/inbuilt_reactions.png" alt="inbuilt_reactions" style="zoom:50%;" />

The UI offers a couple of commons reactions like the like love, thumbs up and so on which makes it easier if you want to quickly adopt them and use them in your app.

However, at times the requirement for your app can be different. Can I remove the default reactions? Can I add custom reactions? Can I be able to apply my own app style to the reactions? Are some of the questions you might ask yourself.

And the good news is....drum rolls :-) Yes you can be able to do all that. From version **4.9** of the SDK you can be able to customize all this. You'll be learning how to customize next.

## Customizing the Reactions UI

In order for you to customize the Reactions, you'll be using the <code>SupportedReactions</code> which allows you to define reactions. It accepts two parameters on it's constructor:

- <code>context</code> - context of your class.
- <code>reactions</code> - This is a Map of keys which hold the reaction type and a <code>ReactionDrawable</code>. This is the parameter you use to add your custom reactions. If you don't provide any reactions, by default it'll use the standard reactions.

A <code>ReactionDrawable</code>is an object that has the reaction icon and also handles the states for the different colors for example the active and inactive state.

To define your own custom reaction, first create a custom <code>ReactionDrawable</code> as follows:

```kotlin
fun clapDrawable(context: Context): SupportedReactions.ReactionDrawable {
    val drawableInactive = ContextCompat.getDrawable(context, R.drawable.ic_clapping)!!
    val drawableActive = ContextCompat.getDrawable(context, R.drawable.ic_clapping)!!.apply {
        setTint(ContextCompat.getColor(context,android.R.color.holo_red_dark ))
    }
    return SupportedReactions.ReactionDrawable(drawableInactive, drawableActive)
}
```

This is a normal function which has <code>context</code> as arguements and returns a <code>ReactionDrawable</code>. As you can see, here you set the <code>drawableInactive</code> and <code>drawableActive</code> icons. For the the active icon you're simply changing the color of the icon.

In the sample project you can check on the <code>ReactionDrawables.kt</code> file which is under the **utils** package for more custom drawables.

Now, you drawables are ready to be used. Next is to create a map of reactions type and the drawables as follows:

```Kotlin
val reactions: Map<String, SupportedReactions.ReactionDrawable> = mapOf(
    "like" to likeDrawable(applicationContext),
    "clap" to clapDrawable(applicationContext),
    "wondering" to wonderingDrawable(applicationContext),
    "brilliant" to brilliantDrawable(applicationContext),
    "handshake" to handShakeDrawable(applicationContext),
)
```

Here you have all your custom reactions and their icons. This is all you need. After defining this, the final thing to do is to make sure your app uses the custom reactions that you've created.

You do this by adding this:

```kotlin
ChatUI.supportedReactions = SupportedReactions(applicationContext, reactions)
```

Here you're passing your reactions to the SDK. And now this is how your reactions look like:

<img src="/Users/harun/AndroidStudioProjects/StreamReactions/images/custom_reactions.png" alt="custom_reactions" style="zoom:50%;" />

Wohoo! As you can now see, all the reactions are custom ones and with different colors!. And as you've noted, it's very easy to achieve this is easy steps. 

In the next section, you'll see how to customize the colors of reaction card and the titles.

## Adding your Custom Styling to Reactions

At times you need the UI for the reactions to adapt to the styling of your app. The Reactions API also supprts this in very simple and straight forwad steps. For the title text you simply add the following in your <code>themes.xml</code> file:

```xml
<item name="streamUiUserReactionsTitleTextColor">@color/white</item>
<item name="streamUiUserReactionsTitleTextSize">18sp</item>
<item name="streamUiUserReactionsTitleTextFont">@font/benton_sans_book</item>
<item name="streamUiUserReactionsTitleTextStyle">italic</item>
<item name="streamUiUserReactionsBackgroundColor">@color/purple_200</item>
```

From the above attributes you can see you can see you can be able to change:

- Color
- Text Size
- Font
- Style
- Card Background.

This is how th final results looks like:

<img src="/Users/harun/AndroidStudioProjects/StreamReactions/images/reactions_custom_style.png" alt="reactions_custom_style" style="zoom:50%;" />

Albeit yours will be different depending on your app colors and styling requirements.

## Conclusion

You've learned how to add, remove, paginate and add cumulative reactions in this tutorial. In the process, you've also learned hpw you can create you own custom reactions and also how to customize reactions in the Stream SDK and also add your own custom reactions.

You can get the full sample project with examples in this tutorial [here](https://github.com/wangerekaharun/StreamReactions).

The Stream SDK also provides out of the box UI Features for reactions which you can explore also incase you need to quickly add reactions in your app without having to worry about the UI and UX part. 

Also as you've seen in this tutorial, it's easy to intergrate reactions to your existing app and use the Reactions API to enrich your app.

