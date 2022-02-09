#!/usr/bin/env python
import plotly.graph_objects as go
import requests
import pandas as pd
import sys
import kaleido


def candlestick():
    r = requests.get(f'http://localhost:8081/getCandles/' + str(sys.argv[2]))
    r = r.json()

    stockdata = r
    stockdata_df = pd.DataFrame(stockdata)

    fig = go.Figure(data=[go.Candlestick(x=stockdata_df['time'],
                                         open=stockdata_df['open'],
                                         high=stockdata_df['high'],
                                         low=stockdata_df['low'],
                                         close=stockdata_df['close'])])

    fig.update_layout(
        title={
            'text': str(sys.argv[2]),
            'y': 0.9,
            'x': 0.5,
            'xanchor': 'center',
            'yanchor': 'top',
        },
        plot_bgcolor='rgb(0,0,0)',
        paper_bgcolor='rgb(0,0,0)',
    )
    fig.update_xaxes(color='rgb(255,255,255)',
                     gridcolor='rgb(255,255,255)')
    fig.update_yaxes(color='rgb(255,255,255)')
    fig.update_xaxes(showgrid=True, gridwidth=1, gridcolor='rgb(50,23,77)')
    fig.update_yaxes(showgrid=True, gridwidth=1, gridcolor='rgb(50,23,77)')
    fig.update_layout(
        font_family="Courier New",
        font_color="white",
        title_font_family="Times New Roman",
        title_font_color="white",
    )
    fig.update_traces(line_width=2, selector=dict(type='candlestick'))
    fig.update_xaxes(
        rangebreaks=[
            # NOTE: Below values are bound (not single values), ie. hide x to y
            #dict(bounds=["sat", "mon"]),  # hide weekends, eg. hide sat to before mon
            dict(bounds=[21, 5], pattern="hour"),  # hide hours outside of 9.30am-4pm
            #доработать время открытия рынков
            #dict(values=["2019-12-25", "2020-12-24"])  # hide holidays (Christmas and New Year's, etc)
        ]
    )
    layout = go.Layout(
        autosize=False,
        width=500,
        height=500
    )
    fig.update_layout(xaxis_rangeslider_visible=False)
    fig.write_image(sys.argv[1], scale=5)


candlestick()
