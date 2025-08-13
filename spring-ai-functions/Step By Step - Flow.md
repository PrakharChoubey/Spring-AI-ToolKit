# 🧭 OpenAI Tool Calling Flow with External API Integration

This document explains how the **OpenAI function-calling** flow works in a Spring AI application where a custom tool (`CurrentWeather`) calls an external weather API. It also breaks down how many **external API calls** are made during the process. Refer the **'Examples'** - actual inputs & output of this implementation.

---

## 📦 Flow Overview

Given a user input like:

> _“What’s the weather like in London?”_

The system follows this sequence:

---

## ✅ Step-by-Step Execution

1. **User Prompt Sent to OpenAI**
    - The system sends the user's question along with tool metadata (e.g., `CurrentWeather` and its input schema) to the OpenAI API.
    - 📡 **Remote Call #1** (to OpenAI/LLM Model)

2. **OpenAI Decides to Call the Tool**
    - The LLM (hosted on OpenAI servers) interprets the user's intent and decides to call the tool function.
    - It extracts arguments like `{"city": "London"}` and returns a function call request.

3. **Spring AI Deserializes the Function Call**
    - The tool input JSON is converted into a `WeatherRequest` object in the application.
    - ✅ **Local operation** (no external call)

4. **The Tool Implementation Calls Weather API**
    - The `WeatherServiceFunction.apply()` method calls an external weather API (like Ninja API or OpenWeather) using the city from the `WeatherRequest`.
    - 🌐 **Remote Call #2** (to Weather API)

5. **Response is Converted to a String**
    - The external API’s response is mapped to the `WeatherResponse` class and then converted into a JSON string.
    - ✅ **Local operation**

6. **Tool Response is Sent Back to OpenAI**
    - Spring AI sends the tool result back to OpenAI so it can be formatted into a natural language answer.
    - 📡 **Remote Call #3** (to OpenAI)

7. **OpenAI Returns Final Answer**
    - OpenAI formats the tool result into a natural language response like:  
      _"It’s currently 26°C in London with 40% humidity."_
    - ✅ OpenAI returns final output to the system.

8. **System Wraps Response into `Answer` Class**
    - The final answer is wrapped into the custom `Answer` object and returned to the client.
    - ✅ **Local operation**

---

## 🔁 External API Call Summary

The system makes a total of **3 external API calls** in this flow:

| Step | Destination   | Purpose                                      |
|------|----------------|----------------------------------------------|
| 1    | OpenAI API     | Send user prompt and tool definitions        |
| 2    | Weather API    | Retrieve live weather data                   |
| 3    | OpenAI API     | Submit tool response to generate final answer|

---
Examples
---

Input:
> _{
"question": **"What is the weather of pondi India"**
}_

Output:
> _{
"answer": "The current weather in Pondicherry, India is as follows:\n\n- **Temperature:** 31°C\n- **Feels Like:** 34°C\n- **Humidity:** 53%\n- **Cloud Coverage:** 100%\n- **Wind Speed:** 4.96 km/h\n- **Wind Direction:** 237° (from the southwest)\n- **Minimum Temperature:** 31°C\n- **Maximum Temperature:** 31°C\n- **Sunrise:** 05:44 AM (GMT)\n- **Sunset:** 06:34 PM (GMT)\n\nIt seems to be quite cloudy with a warm temperature."
}_

Input:
> _{
"question": **"dilli ka weather batao"**
}_

Output:
> _{
"answer": "दिल्ली का मौसम इस प्रकार है:\n\n- **वर्तमान तापमान:** 34°C\n- **महसूस होता तापमान:** 41°C\n- **कम से कम तापमान:** 34°C\n- **अधिकतम तापमान:** 34°C\n- **आर्द्रता:** 59%\n- **बादल का प्रतिशत:** 75%\n- **हवा की गति:** 2.57 किमी/घंटा\n- **हवा की दिशा:** 260° (पश्चिम)\n\nसूर्योदय और सूर्यास्त का समय:\n- **सूर्योदय:** 5:52 AM\n- **सूर्यास्त:** 5:59 PM\n\nयदि आपको और जानकारी चाहिए तो बताएं!"
}
